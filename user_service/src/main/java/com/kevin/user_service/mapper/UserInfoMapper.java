package com.kevin.user_service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kevin.user_service.pojo.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 20349
 * @description 针对表【user_info(用户信息表)】的数据库操作Mapper
 * @createDate 2023-03-30 21:51:11
 * @Entity com.kevin.user_service.pojo.UserInfo
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

}




