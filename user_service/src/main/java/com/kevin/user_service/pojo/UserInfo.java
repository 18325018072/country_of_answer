package com.kevin.user_service.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

/**
 * 用户信息表
 *
 * @TableName user_info
 */
@TableName(value = "user_info")
@Data
@NoArgsConstructor
public class UserInfo implements Serializable {
	/**
	 * 用户ID
	 */
	@TableId(type = IdType.AUTO)
	private Integer userId;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 电话号码
	 */
	private String tel;

	/**
	 * 本月签到历史
	 */
	private String signHistory;

	/**
	 * 最近试卷
	 */
	private String recentTest;

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	public UserInfo(String userName, String tel) {
		this.userName = userName;
		this.tel = tel;
	}

	/**
	 * 用户名随机生成
	 *
	 * @param tel
	 */
	public UserInfo(String tel) {
		this.userName = "用户_" + UUID.randomUUID().toString().substring(0, 8);
		this.tel = tel;
	}
}