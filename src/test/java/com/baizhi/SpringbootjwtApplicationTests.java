package com.baizhi;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.HashMap;

//@SpringBootTest
class SpringbootjwtApplicationTests {

	@Test
	void contextLoads() {

		HashMap<String, Object> map = new HashMap<>();

		Calendar instance = Calendar.getInstance();
		instance.add(Calendar.SECOND,300);

		String token = JWT.create()
//				.withHeader(map) //hewader
				.withClaim("userId", 21)//payload
				.withClaim("username", "john")
				.withExpiresAt(instance.getTime()) // 指定令牌過期時間
				.sign(Algorithm.HMAC256("!Q@W#E$R"));// 簽名

		System.out.println(token);
	}

	@Test
	public void test(){
		JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("!Q@W#E$R")).build();

		DecodedJWT verify = jwtVerifier.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDU1NjU5OTksInVzZXJJZCI6MjEsInVzZXJuYW1lIjoiam9obiJ9.00dyX7Lm4IIe8xyG5oZl0bQSn-6Q764EBsWPKKcDn80");

//		System.out.println(verify.getClaim("userId").asInt());
//		System.out.println(verify.getClaim("username").asString());
		System.out.println(verify.getClaims().get("userId").asInt());
		System.out.println(verify.getClaims().get("username").asString());
		System.out.println(verify.getExpiresAt());
	}

}
