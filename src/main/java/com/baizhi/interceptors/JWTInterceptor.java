package com.baizhi.interceptors;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baizhi.utils.JWTUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


public class JWTInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, Object> map = new HashMap<>();
        //獲取請求頭的令牌
        String token = request.getHeader("token");

        try {
            JWTUtils.verify(token);//驗證令牌
            return true;
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
            map.put("msg", "無效簽名");
        } catch (TokenExpiredException e){
            e.printStackTrace();
            map.put("msg", "token過期");
        } catch (AlgorithmMismatchException e){
            e.printStackTrace();
            map.put("msg", "token算法不一致");
        } catch (Exception e){
            e.printStackTrace();
            map.put("msg", "token無效");
        }
        map.put("states", false);
        //map轉為json
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().println(json);
        return false;
    }
}
