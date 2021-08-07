package com.erizeez.genshinimpactdamagecalculatorserver.interceptor;

import com.erizeez.genshinimpactdamagecalculatorserver.util.http.CookieUtil;
import com.erizeez.genshinimpactdamagecalculatorserver.util.security.TokenUtil;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        response.setCharacterEncoding("utf-8");
        Cookie token = CookieUtil.getCookie(request.getCookies(), "token");
        if (token != null && TokenUtil.verifyToken(token.getValue())) {
            return true;
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("msg", "Invalid token.");
            map.put("result", "-1");
            response.getWriter().write(JSONObject.toJSONString(map));
            return false;
        }
    }
}
