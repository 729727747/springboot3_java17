package com.example.deepseek.client;

import com.example.deepseek.config.DeepSeekConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class DeepSeekClient {
    
    private static final Logger log = LoggerFactory.getLogger(DeepSeekClient.class);

    private final WebClient webClient;
    private final DeepSeekConfig deepSeekConfig;

    @Autowired
    public DeepSeekClient(DeepSeekConfig deepSeekConfig) {
        this.deepSeekConfig = deepSeekConfig;
        this.webClient = WebClient.builder()
                .baseUrl(deepSeekConfig.getBaseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + deepSeekConfig.getKey())
                .build();
    }

    /**
     * 发送问答请求到DeepSeek API
     * @param requestJson 请求JSON字符串
     * @return 响应JSON字符串
     */
    public Mono<String> sendQaRequest(String requestJson) {
        return webClient.post()
                .uri("/chat/completions")
                .bodyValue(requestJson)
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(Long.parseLong(deepSeekConfig.getTimeout())))
                .doOnNext(response -> log.info("DeepSeek API response: {}", response))
                .doOnError(throwable -> log.error("Error calling DeepSeek API", throwable));
    }
    
    /**
     * 发送流式问答请求到DeepSeek API
     * @param requestJson 请求JSON字符串
     * @return 响应流
     */
    public Flux<String> sendStreamingQaRequest(String requestJson) {
        return webClient.post()
                .uri("/chat/completions")
                .bodyValue(requestJson)
                .retrieve()
                .bodyToFlux(String.class)
                .timeout(Duration.ofSeconds(Long.parseLong(deepSeekConfig.getTimeout())))
                .doOnNext(response -> log.info("DeepSeek API streaming response: {}", response))
                .doOnError(throwable -> log.error("Error calling DeepSeek API for streaming", throwable));
    }
}