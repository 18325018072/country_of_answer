package com.kevin.user_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevin.user_service.mapper.UserInfoMapper;
import com.kevin.user_service.pojo.UserInfo;
import com.kevin.user_service.service.UserInfoService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Calendar;

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

	/**
	 * 获取该用户最近查看的试卷
	 *
	 * @param tel 用户电话
	 * @return [{"testId":"45621","testName":"机组试卷"},{"testId":"81726","testName":"计科试卷"}]
	 */
	@Override
	public String getRecentTest(String tel) {
		QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("tel", tel);
		return getOne(queryWrapper).getRecentTest();
	}

	/**
	 * 用户签到
	 */
	@Override
	public void sign(int userId) {
		UserInfo userInfo = getById(userId);
		String oldSignHistoryJson = userInfo.getSignHistory();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Integer[] oldSignHistory = objectMapper.readValue(oldSignHistoryJson, Integer[].class);
			int today = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
			//如果已签到，则不签了
			for (Integer day : oldSignHistory) {
				if (today == day) {
					return;
				}
			}
			//如果没签到，则签到
			Integer[] newSignHistory = Arrays.copyOf(oldSignHistory, oldSignHistory.length + 1);
			newSignHistory[newSignHistory.length - 1] = today;
			UpdateWrapper<UserInfo> updateWrapper = new UpdateWrapper<>();
			updateWrapper.set("sign_history", objectMapper.writeValueAsString(newSignHistory));
			updateWrapper.eq("user_id", userId);
			update(updateWrapper);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}




