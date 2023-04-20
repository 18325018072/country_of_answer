package com.kevin.test_service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kevin.test_service.pojo.TestUserResult;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 20349
 * @description 针对表【test_user_result(用户-试卷答题情况)】的数据库操作Mapper
 * @createDate 2023-04-19 23:56:00
 * @Entity com.kevin.test_service.pojo.TestUserResult
 */
@Mapper
public interface TestUserResultMapper extends BaseMapper<TestUserResult> {

}




