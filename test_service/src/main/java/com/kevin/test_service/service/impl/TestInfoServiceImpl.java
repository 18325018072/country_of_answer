package com.kevin.test_service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kevin.test_service.pojo.TestInfo;
import com.kevin.test_service.service.TestInfoService;
import com.kevin.test_service.mapper.TestInfoMapper;
import org.springframework.stereotype.Service;

/**
 * @author 20349
 * @description 针对表【test_info(试卷信息)】的数据库操作Service实现
 * @createDate 2023-04-12 17:10:49
 */
@Service
public class TestInfoServiceImpl extends ServiceImpl<TestInfoMapper, TestInfo>
		implements TestInfoService {

}




