package com.erizeez.genshinimpactdamagecalculatorserver.util.http;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;

public class CookieUtil {
    public static Cookie getCookie(Cookie[] cookies, String name) {
        if (cookies == null) return null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie;
            }
        }
        return null;
    }

    public static Map<String, Cookie> getCookieMap(Cookie[] cookies) {
        Map<String, Cookie> map = new HashMap<>();
        for (Cookie cookie : cookies) {
            map.put(cookie.getName(), cookie);
        }

        return map;
    }
}
