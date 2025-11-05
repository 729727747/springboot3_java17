package com.example.deepseek.controller;

import com.example.deepseek.model.request.QaRequest;
import com.example.deepseek.model.response.QaResponse;
import com.example.deepseek.service.QaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/qa")
@CrossOrigin
public class QaController {

    private final QaService qaService;

    @Autowired
    public QaController(QaService qaService) {
        this.qaService = qaService;
    }

    /**
     * 问答接口
     * @param request 问答请求
     * @return 问答响应
     */
    @PostMapping("/chat")
    public QaResponse chat(@RequestBody QaRequest request) {
        return qaService.processQaRequest(request);
    }
    
    /**
     * 流式问答接口
     * @param request 问答请求
     * @return 流式响应
     */
    @PostMapping(value = "/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamChat(@RequestBody QaRequest request) {
        return qaService.processStreamingQaRequest(request);
    }

    /**
     * 健康检查接口
     * @return 状态信息
     */
    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}