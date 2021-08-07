package com.erizeez.genshinimpactdamagecalculatorserver.controller;

import com.erizeez.genshinimpactdamagecalculatorserver.entity.User;
import com.erizeez.genshinimpactdamagecalculatorserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
                map.put("success", "-1");

                return map;
            }

            userService.insertUser(register_form);
            User user = userService.selectUserByUserName(register_form.getUserName());

            if (user == null) {
                map.put("msg", "Fail to insert new user.");
                map.put("success", "-1");
            } else {
                Cookie cookie = new Cookie("token", TokenUtil.makeToken(user));
                cookie.setPath("/");
                cookie.setMaxAge(TokenUtil.EXPIRE_TIME_MIN * 60);
                response.addCookie(cookie);
                map.put("msg", "Register process is complete.");
                map.put("success", "0");
            }
        } else {
            map.put("msg", "Register form is incomplete.");
            map.put("success", "-1");
        }

        return map;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(@RequestBody User login_form) {
        Map<String, Object> response = new HashMap<>();


        return response;
    }
}
