package com.kevin.user_service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kevin.user_service.pojo.UserInfo;

/**
 * @author 20349
 * @description 针对表【user_info(用户信息表)】的数据库操作Service
 * @createDate 2023-03-30 21:51:11
 */
public interface UserInfoService extends IService<UserInfo> {

	/**
	 * 根据电话号码获取用户信息
	 */
	UserInfo getUser(String tel);

	/**
	 * 用手机号码注册新账号
	 */
	void register(String tel);

	/**
	 * 获取该用户最近查看的试卷
	 *
	 * @param tel 用户电话
	 * @return [{45621,"机组试卷"},{81726,"计科试卷"}]
	 */
	String getRecentTest(String tel);

	/**
	 * 用户签到
	 */
	void sign(int userId);
}
