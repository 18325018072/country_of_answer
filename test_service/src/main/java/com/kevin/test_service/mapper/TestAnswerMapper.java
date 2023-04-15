package com.kevin.test_service.mapper;

import com.kevin.test_service.pojo.TestAnswer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 20349
 * @description 针对表【test_answer(试卷答案)】的数据库操作Mapper
 * @createDate 2023-04-12 17:28:46
 * @Entity com.kevin.test_service.pojo.TestAnswer
 */
@Mapper
public interface TestAnswerMapper extends BaseMapper<TestAnswer> {

}




