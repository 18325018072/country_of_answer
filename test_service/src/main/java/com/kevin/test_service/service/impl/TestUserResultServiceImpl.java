package com.kevin.test_service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kevin.test_service.mapper.TestUserResultMapper;
import com.kevin.test_service.pojo.TestUserResult;
import com.kevin.test_service.service.TestUserResultService;
import org.springframework.stereotype.Service;

/**
 * @author 20349
 * @description 针对表【test_user_result(用户-试卷答题情况)】的数据库操作Service实现
 * @createDate 2023-04-19 23:56:00
 */
@Service
public class TestUserResultServiceImpl extends ServiceImpl<TestUserResultMapper, TestUserResult>
		implements TestUserResultService {

}




