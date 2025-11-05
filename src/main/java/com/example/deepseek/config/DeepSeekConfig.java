package com.example.deepseek.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "deepseek.api")
public class DeepSeekConfig {
    private String baseUrl;
    private String key;
    private String model;
    private String timeout;
    
    // 显式添加getter方法以解决编译问题
    public String getBaseUrl() {
        return baseUrl;
    }
    
    public String getKey() {
        return key;
    }
    
    public String getModel() {
        return model;
    }
    
    public String getTimeout() {
        return timeout;
    }
}