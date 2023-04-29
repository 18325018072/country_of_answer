package com.kevin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 基础响应包
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponsePack {
	public static final int SUCCESS_CODE = 0;
	public static final int FAIL_CODE = 1;

	/**
	 * 状态.请用本类的静态字段
	 */
	private Integer status;
	private Object object;
	private String info;

	public static BaseResponsePack simpleSuccess() {
		return new BaseResponsePack(SUCCESS_CODE, null, "success");
	}

	public static BaseResponsePack simpleSuccess(Object object) {
		return new BaseResponsePack(SUCCESS_CODE, object, "success");
	}

	public static BaseResponsePack simpleFail(String reason) {
		return new BaseResponsePack(FAIL_CODE, null, reason);
	}
}