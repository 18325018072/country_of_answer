package com.kevin.test_service.mapper;

import com.kevin.test_service.pojo.TestInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author 20349
 * @description 针对表【test_info(试卷信息)】的数据库操作Mapper
 * @createDate 2023-04-12 17:10:49
 * @Entity com.kevin.test_service.pojo.TestInfo
 */
@Mapper
public interface TestInfoMapper extends BaseMapper<TestInfo> {

}




