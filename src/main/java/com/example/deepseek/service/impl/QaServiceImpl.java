package com.example.deepseek.service.impl;

import com.example.deepseek.client.DeepSeekClient;
import com.example.deepseek.config.DeepSeekConfig;
import com.example.deepseek.entity.Question;
import com.example.deepseek.mapper.QuestionMapper;
import com.example.deepseek.model.request.QaRequest;
import com.example.deepseek.model.response.QaResponse;
import com.example.deepseek.service.QaService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class QaServiceImpl implements QaService {

    @Autowired
    private DeepSeekClient deepSeekClient;
    
    @Autowired
    private DeepSeekConfig deepSeekConfig;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public QaResponse processQaRequest(QaRequest request) {
        try {
            // 构建请求JSON
            String requestJson = buildRequestJson(request);
            
            // 调用DeepSeek API
            Mono<String> responseMono = deepSeekClient.sendQaRequest(requestJson);
            
            // 阻塞获取响应（在实际生产环境中，应该使用异步处理）
            String responseJson = responseMono.block();
            
            // 解析响应
            QaResponse response = parseResponse(responseJson);
            
            // 保存到数据库
            saveToDatabase(request, response);
            
            return response;
        } catch (Exception e) {
            // 创建错误响应
            QaResponse errorResponse = new QaResponse();
            errorResponse.setCode(500);
            errorResponse.setMessage("调用DeepSeek API时发生错误: " + e.getMessage());
            return errorResponse;
        }
    }
    
    @Override
    public Flux<String> processStreamingQaRequest(QaRequest request) {
        try {
            // 构建请求JSON，启用流式传输
            String requestJson = buildStreamingRequestJson(request);
            
            // 调用DeepSeek API流式接口
            Flux<String> responseFlux = deepSeekClient.sendStreamingQaRequest(requestJson);
            
            // 返回流式响应
            return responseFlux;
        } catch (Exception e) {
            // 错误处理
            return Flux.error(e);
        }
    }

    /**
     * 构建流式请求JSON
     * @param request 问答请求
     * @return 请求JSON字符串
     */
    private String buildStreamingRequestJson(QaRequest request) throws Exception {
        // 创建根对象
        JsonNode rootNode = objectMapper.createObjectNode();
        
        // 设置模型
        String model = request.getModel() != null ? request.getModel() : deepSeekConfig.getModel();
        ((com.fasterxml.jackson.databind.node.ObjectNode) rootNode).put("model", model);
        
        // 创建消息数组
        com.fasterxml.jackson.databind.node.ArrayNode messagesNode = objectMapper.createArrayNode();
        
        // 添加历史记录（如果有）
        if (request.getHistory() != null) {
            for (QaRequest.Message message : request.getHistory()) {
                com.fasterxml.jackson.databind.node.ObjectNode messageNode = objectMapper.createObjectNode();
                messageNode.put("role", message.getRole());
                messageNode.put("content", message.getContent());
                messagesNode.add(messageNode);
            }
        }
        
        // 添加当前问题
        com.fasterxml.jackson.databind.node.ObjectNode userMessageNode = objectMapper.createObjectNode();
        userMessageNode.put("role", "user");
        userMessageNode.put("content", request.getQuestion());
        messagesNode.add(userMessageNode);
        
        // 将消息数组添加到根对象
        ((com.fasterxml.jackson.databind.node.ObjectNode) rootNode).set("messages", messagesNode);
        
        // 启用流式传输
        ((com.fasterxml.jackson.databind.node.ObjectNode) rootNode).put("stream", true);
        
        // 转换为JSON字符串
        return objectMapper.writeValueAsString(rootNode);
    }

    /**
     * 构建请求JSON
     * @param request 问答请求
     * @return 请求JSON字符串
     */
    private String buildRequestJson(QaRequest request) throws Exception {
        // 创建根对象
        JsonNode rootNode = objectMapper.createObjectNode();
        
        // 设置模型
        String model = request.getModel() != null ? request.getModel() : deepSeekConfig.getModel();
        ((com.fasterxml.jackson.databind.node.ObjectNode) rootNode).put("model", model);
        
        // 创建消息数组
        com.fasterxml.jackson.databind.node.ArrayNode messagesNode = objectMapper.createArrayNode();
        
        // 添加历史记录（如果有）
        if (request.getHistory() != null) {
            for (QaRequest.Message message : request.getHistory()) {
                com.fasterxml.jackson.databind.node.ObjectNode messageNode = objectMapper.createObjectNode();
                messageNode.put("role", message.getRole());
                messageNode.put("content", message.getContent());
                messagesNode.add(messageNode);
            }
        }
        
        // 添加当前问题
        com.fasterxml.jackson.databind.node.ObjectNode userMessageNode = objectMapper.createObjectNode();
        userMessageNode.put("role", "user");
        userMessageNode.put("content", request.getQuestion());
        messagesNode.add(userMessageNode);
        
        // 将消息数组添加到根对象
        ((com.fasterxml.jackson.databind.node.ObjectNode) rootNode).set("messages", messagesNode);
        
        // 转换为JSON字符串
        return objectMapper.writeValueAsString(rootNode);
    }

    /**
     * 解析响应JSON
     * @param responseJson 响应JSON字符串
     * @return 问答响应对象
     */
    private QaResponse parseResponse(String responseJson) throws Exception {
        QaResponse response = new QaResponse();
        
        // 解析JSON
        JsonNode rootNode = objectMapper.readTree(responseJson);
        
        // 获取第一个选择项
        JsonNode choicesNode = rootNode.get("choices");
        if (choicesNode != null && choicesNode.isArray() && choicesNode.size() > 0) {
            JsonNode firstChoiceNode = choicesNode.get(0);
            JsonNode messageNode = firstChoiceNode.get("message");
            if (messageNode != null) {
                JsonNode contentNode = messageNode.get("content");
                if (contentNode != null) {
                    response.getData().setAnswer(contentNode.asText());
                }
            }
        }
        
        // 获取使用情况
        JsonNode usageNode = rootNode.get("usage");
        if (usageNode != null) {
            response.getData().getUsage().setPromptTokens(usageNode.get("prompt_tokens").asInt(0));
            response.getData().getUsage().setCompletionTokens(usageNode.get("completion_tokens").asInt(0));
            response.getData().getUsage().setTotalTokens(usageNode.get("total_tokens").asInt(0));
        }
        
        // 设置成功状态
        response.setCode(200);
        response.setMessage("success");
        
        return response;
    }

    /**
     * 保存问答记录到数据库
     * @param request 问答请求
     * @param response 问答响应
     */
    private void saveToDatabase(QaRequest request, QaResponse response) {
        try {
            Question question = new Question();
            question.setQuestionContent(request.getQuestion());
            question.setAnswerContent(response.getData() != null ? response.getData().getAnswer() : "");
            question.setModelUsed(request.getModel() != null ? request.getModel() : deepSeekConfig.getModel());
            question.setPromptTokens(response.getData().getUsage().getPromptTokens());
            question.setCompletionTokens(response.getData().getUsage().getCompletionTokens());
            question.setTotalTokens(response.getData().getUsage().getTotalTokens());
            question.setTotalTokens(response.getData().getUsage().getTotalTokens());
            
            questionMapper.insert(question);
        } catch (Exception e) {
            // 记录错误但不中断流程
            e.printStackTrace();
        }
    }
}