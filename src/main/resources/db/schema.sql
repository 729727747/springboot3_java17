CREATE DATABASE IF NOT EXISTS deepseek_db_dev CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE deepseek_db_dev;

CREATE TABLE IF NOT EXISTS questions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    question_content TEXT NOT NULL COMMENT '问题内容',
    answer_content TEXT COMMENT 'AI回答内容',
    model_used VARCHAR(100) COMMENT '使用的模型',
    prompt_tokens INT DEFAULT 0 COMMENT '提示词token数',
    completion_tokens INT DEFAULT 0 COMMENT '完成token数',
    total_tokens INT DEFAULT 0 COMMENT '总token数',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='问答记录表';