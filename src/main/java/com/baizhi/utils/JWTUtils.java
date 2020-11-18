package com.baizhi.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class JWTUtils {
    private static final String SING = "!Q@W#E$R%T^Y";

    /**
     * 生成Token
     */
    public static String getToken(Map<String, String> map){

        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND,70);

        JWTCreator.Builder builder = JWT.create();

        //payload
        map.forEach((k, v) ->{
            builder.withClaim(k,v);
        });

        String token = builder.withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(SING));

        return token;
    }
    /**
     * 驗證Token 合法性
     */
    public static DecodedJWT verify(String token){
        return JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
    }

    /**
     * 獲取Token信息方法
     */
//    public static DecodedJWT getTokenImfo(String token){
//        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
//        return verify;
//    }
}
