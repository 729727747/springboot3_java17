-- 创建数据库
CREATE DATABASE IF NOT EXISTS deepseek_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE deepseek_db;

-- 创建问题记录表
CREATE TABLE IF NOT EXISTS question (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
    question TEXT NOT NULL COMMENT '问题内容',
    answer TEXT COMMENT '回答内容',
    model VARCHAR(100) DEFAULT 'deepseek-chat' COMMENT '使用的模型',
    prompt_tokens INT DEFAULT 0 COMMENT '提示词token数',
    completion_tokens INT DEFAULT 0 COMMENT '完成token数',
    total_tokens INT DEFAULT 0 COMMENT '总token数',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='问答记录表';