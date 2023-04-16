package com.kevin.user_service.util;

import com.kevin.user_service.pojo.UserInfo;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class JwtUtil {

	@Value("${jwt.secretKey}")
	private String secretKey;

	@Value("${jwt.tokenHead}")
	private String tokenHead;

	/**
	 * 过期时间：1小时
	 */
	private static final long EXPIRE = 3600000L;

	/**
	 * 构建 JWT.
	 */
	public String createJwt(UserInfo userInfo) {
		Map<String, Object> userInfoMap = new HashMap<>(4);
		userInfoMap.put("userName", userInfo.getUserName());
		userInfoMap.put("userId", userInfo.getUserId());
		userInfoMap.put("recentTest", userInfo.getRecentTest());
		userInfoMap.put("signHistory", userInfo.getSignHistory());
		return tokenHead + Jwts.builder()
				.setId(UUID.randomUUID().toString().replaceAll("-", ""))
				.setSubject(userInfo.getUserId() + "")
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.setClaims(userInfoMap)
				.compressWith(CompressionCodecs.DEFLATE)
				.compact();
	}


	/**
	 * 从 jwt 提取 Claims
	 */
	public Claims parseJwt(String jwtString) {
		JwtParser jwtParser = Jwts.parser().setSigningKey(secretKey);
		return jwtParser.parseClaimsJws(jwtString).getBody();
	}
}
