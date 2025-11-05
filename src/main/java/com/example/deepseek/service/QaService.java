package com.example.deepseek.service;

import com.example.deepseek.model.request.QaRequest;
import com.example.deepseek.model.response.QaResponse;
import reactor.core.publisher.Flux;

public interface QaService {
    /**
     * 处理问答请求
     * @param request 问答请求
     * @return 问答响应
     */
    QaResponse processQaRequest(QaRequest request);
    
    /**
     * 处理流式问答请求
     * @param request 问答请求
     * @return 流式响应
     */
    Flux<String> processStreamingQaRequest(QaRequest request);
}