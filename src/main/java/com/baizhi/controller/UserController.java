package com.baizhi.controller;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import com.baizhi.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user/login")
    public Map<String, Object> login(User user){
        log.info("用戶名: [{}]",user.getName());
        log.info("密碼: [{}]",user.getPassword());
        Map<String, Object> map = new HashMap<>();

        try {
            User userDB = userService.login(user);
            Map<String, String> payload = new HashMap<>();
            payload.put("id", userDB.getId());
            payload.put("name", userDB.getName());
            //token
            String token = JWTUtils.getToken(payload);

            map.put("state", true);
            map.put("msg", "認證成功");
            map.put("token", token);//響應token
        } catch (Exception e) {
            map.put("state", false);
            map.put("msg", e.getMessage());
        }

        return map;
    }

    /*@PostMapping("/user/test")
    public Map<String, Object> test(String token){
        Map<String, Object> map = new HashMap<>();
        map.put("state", true);
        map.put("msg", "請求成功!");
        log.info("當前token為: [{}]", token);
        try {
            DecodedJWT verify = JWTUtils.verify(token);//驗證令牌

            return map;
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
        map.put("state", false);
        return map;
    }*/

    @PostMapping("/user/test")
    public Map<String, Object> test(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        String token = request.getHeader("token");
        DecodedJWT verify = JWTUtils.verify(token);
        log.info("用戶id: [{}]", verify.getClaim("id").asString());
        log.info("用戶name: [{}]", verify.getClaim("name").asString());

        map.put("state", true);
        map.put("msg", "請求成功!");
        return map;
    }
}
