package com.kevin.test_service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kevin.test_service.pojo.TestQuestions;
import com.kevin.test_service.service.TestQuestionsService;
import com.kevin.test_service.mapper.TestQuestionsMapper;
import org.springframework.stereotype.Service;

/**
 * @author 20349
 * @description 针对表【test_questions】的数据库操作Service实现
 * @createDate 2023-04-12 17:28:56
 */
@Service
public class TestQuestionsServiceImpl extends ServiceImpl<TestQuestionsMapper, TestQuestions>
		implements TestQuestionsService {

}




