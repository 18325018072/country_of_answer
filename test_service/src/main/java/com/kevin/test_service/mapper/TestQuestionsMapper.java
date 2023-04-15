package com.kevin.test_service.mapper;

import com.kevin.test_service.pojo.TestQuestions;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 20349
 * @description 针对表【test_questions】的数据库操作Mapper
 * @createDate 2023-04-12 17:28:56
 * @Entity com.kevin.test_service.pojo.TestQuestions
 */
@Mapper
public interface TestQuestionsMapper extends BaseMapper<TestQuestions> {

}




