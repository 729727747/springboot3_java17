package com.example.deepseek.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("questions") // 指定数据库表名
public class Question {
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 问题内容
     */
    @TableField("question_content")
    private String questionContent;

    /**
     * AI回答内容
     */
    @TableField("answer_content")
    private String answerContent;

    /**
     * 使用的模型
     */
    @TableField("model_used")
    private String modelUsed;

    /**
     * 提示词token数
     */
    @TableField("prompt_tokens")
    private Integer promptTokens;

    /**
     * 完成token数
     */
    @TableField("completion_tokens")
    private Integer completionTokens;

    /**
     * 总token数
     */
    @TableField("total_tokens")
    private Integer totalTokens;

    /**
     * 创建时间
     */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}