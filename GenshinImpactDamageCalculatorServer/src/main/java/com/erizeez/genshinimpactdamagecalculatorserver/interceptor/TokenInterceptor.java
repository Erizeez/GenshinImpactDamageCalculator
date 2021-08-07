package com.erizeez.genshinimpactdamagecalculatorserver.interceptor;

import com.erizeez.genshinimpactdamagecalculatorserver.util.security.TokenUtil;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        response.setCharacterEncoding("utf-8");
        String token = request.getHeader("token");
        if (token != null && TokenUtil.verifyToken(token)) {
            return true;
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("msg", "Invalid token.");
            map.put("success", "-1");
            response.getWriter().write(JSONObject.toJSONString(map));
            return false;
        }
    }
}
