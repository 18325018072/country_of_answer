package com.kevin.user_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kevin.user_service.mapper.UserInfoMapper;
import com.kevin.user_service.pojo.UserInfo;
import com.kevin.user_service.service.UserInfoService;
import org.springframework.stereotype.Service;

/**
 * @author 20349
 * @description 针对表【user_info(用户信息表)】的数据库操作Service实现
 * @createDate 2023-03-30 21:51:11
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
		implements UserInfoService {

	/**
	 * 根据电话号码获取用户信息
	 */
	@Override
	public UserInfo getUser(String tel) {
		QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("tel", tel);
		return getOne(queryWrapper);
	}

	/**
	 * 用手机号码注册新账号
	 */
	@Override
	public void register(String tel) {
		//数据库注册
		save(new UserInfo(tel));
	}
}




