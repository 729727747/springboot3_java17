package com.example.deepseek.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.deepseek.entity.Question;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper extends BaseMapper<Question> {
}