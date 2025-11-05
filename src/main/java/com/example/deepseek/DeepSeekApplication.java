package com.example.deepseek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DeepSeekApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeepSeekApplication.class, args);
    }

}