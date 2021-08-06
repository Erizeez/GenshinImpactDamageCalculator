package com.erizeez.genshinimpactdamagecalculatorserver.controller;


import com.erizeez.genshinimpactdamagecalculatorserver.entity.User;
import com.erizeez.genshinimpactdamagecalculatorserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> register(@RequestBody User register_form) {
        Map<String, Object> response = new HashMap<>();

        if (register_form.getUserName() != null
                && register_form.getPassWord() != null
                && register_form.getNickName() != null) {
            if (userService.selectUserByUserName(register_form.getUserName()) != null) {
                response.put("msg", "Repeat username is not permitted.");
                response.put("success", "-1");

                return response;
            }

            userService.insertUser(register_form);

            if (userService.selectUserByUserName(register_form.getUserName()) == null) {
                response.put("msg", "Fail to insert new user.");
                response.put("success", "-1");
            } else {
                response.put("msg", "Register process is complete.");
                response.put("success", "0");
            }
        } else {
            response.put("msg", "Register form is incomplete.");
            response.put("success", "-1");
        }

        return response;
    }


}
