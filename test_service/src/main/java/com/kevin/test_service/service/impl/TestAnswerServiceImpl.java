package com.kevin.test_service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kevin.test_service.pojo.TestAnswer;
import com.kevin.test_service.service.TestAnswerService;
import com.kevin.test_service.mapper.TestAnswerMapper;
import org.springframework.stereotype.Service;

/**
 * @author 20349
 * @description 针对表【test_answer(试卷答案)】的数据库操作Service实现
 * @createDate 2023-04-12 17:28:46
 */
@Service
public class TestAnswerServiceImpl extends ServiceImpl<TestAnswerMapper, TestAnswer>
		implements TestAnswerService {

}




