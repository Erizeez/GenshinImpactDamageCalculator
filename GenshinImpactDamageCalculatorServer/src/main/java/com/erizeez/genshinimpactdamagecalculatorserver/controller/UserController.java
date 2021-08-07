package com.erizeez.genshinimpactdamagecalculatorserver.controller;

import com.erizeez.genshinimpactdamagecalculatorserver.entity.User;
import com.erizeez.genshinimpactdamagecalculatorserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.erizeez.genshinimpactdamagecalculatorserver.util.security.TokenUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> register(@RequestBody User register_form, HttpServletResponse response) throws IOException {
        Map<String, Object> map = new HashMap<>();

        if (register_form.getUserName() != null
                && register_form.getPassWord() != null
                && register_form.getNickName() != null) {
            if (userService.selectUserByUserName(register_form.getUserName()) != null) {
                map.put("msg", "Repeat username is not permitted.");
                map.put("result", "-1");

                return map;
            }

            register_form.setLoginTime(new Timestamp(new Date().getTime()));
            userService.insertUser(register_form);
            User user = userService.selectUserByUserName(register_form.getUserName());

            if (user == null) {
                map.put("msg", "Fail to insert new user.");
                map.put("result", "-1");
            } else {
                Cookie cookie = new Cookie("token", TokenUtil.makeToken(user));
                cookie.setPath("/");
                cookie.setMaxAge(TokenUtil.EXPIRE_TIME_MIN * 60);
                response.addCookie(cookie);
                map.put("msg", "Register process is complete.");
                map.put("result", "0");
            }
        } else {
            map.put("msg", "Register form is incomplete.");
            map.put("result", "-1");
        }

        return map;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(@RequestBody User login_form, HttpServletResponse response) throws IOException {
        Map<String, Object> map = new HashMap<>();

        if (login_form.getPassWord() != null) {
            User user = null;
            if (login_form.getUserName() != null) {
                user = userService.selectUserByUserName(login_form.getUserName());
            } else if (login_form.getuID() != null) {
                user = userService.selectUserByUID(login_form.getuID());
            } else {
                map.put("msg", "Login form is incomplete.");
                map.put("result", "-1");
                return map;
            }

            if (user == null){
                map.put("msg", "There is no user.");
                map.put("result", "-1");
            } else if (user.getPassWord() != login_form.getPassWord()){
                map.put("msg", "Password error.");
                map.put("result", "-1");
            } else {
                Cookie cookie = new Cookie("token", TokenUtil.makeToken(user));
                cookie.setPath("/");
                cookie.setMaxAge(TokenUtil.EXPIRE_TIME_MIN * 60);
                response.addCookie(cookie);
                map.put("msg", "Login process is complete.");
                map.put("result", "0");
            }
        } else {
            map.put("msg", "Login form is incomplete.");
            map.put("result", "-1");
        }

        return map;
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> test(@RequestBody User login_form, HttpServletResponse response) throws IOException {
        Map<String, Object> map = new HashMap<>();

        return map;
    }
}
