package com.kevin.user_service.util;

import com.kevin.user_service.pojo.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
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
		return tokenHead + Jwts.builder()
				.setId(UUID.randomUUID().toString().replaceAll("-", ""))
				.setSubject(String.valueOf(userInfo.getUserId()))
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.setClaims(userInfo.toSafeMap())
				.compressWith(CompressionCodecs.DEFLATE)
				.compact();
	}


	/**
	 * 从 jwt 提取 Claims
	 */
	public UserInfo parseJwt(String jwtString) {
		Claims body = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtString).getBody();
		return new UserInfo((Integer) body.get("userId")
				, (String) body.get("userName"), null
				, (String) body.get("signHistory")
				, (String) body.get("recentTest"));
	}

	/**
	 * jwt是否过期
	 */
	public boolean isTokenExpired(Claims claims) {
		return claims.getExpiration().before(new Date());
	}
}
