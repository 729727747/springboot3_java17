package com.example.deepseek.model.request;

import java.util.List;

public class QaRequest {
    /**
     * 用户问题
     */
    private String question;

    /**
     * 对话历史记录
     */
    private List<Message> history;

    /**
     * 指定模型（可选）
     */
    private String model;
    
    // Getter and Setter methods
    public String getQuestion() {
        return question;
    }
    
    public void setQuestion(String question) {
        this.question = question;
    }
    
    public List<Message> getHistory() {
        return history;
    }
    
    public void setHistory(List<Message> history) {
        this.history = history;
    }
    
    public String getModel() {
        return model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }

    public static class Message {
        private String role;
        private String content;
        
        // Getter and Setter methods
        public String getRole() {
            return role;
        }
        
        public void setRole(String role) {
            this.role = role;
        }
        
        public String getContent() {
            return content;
        }
        
        public void setContent(String content) {
            this.content = content;
        }
    }
}