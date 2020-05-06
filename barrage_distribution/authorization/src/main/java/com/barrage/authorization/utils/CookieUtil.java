package com.barrage.authorization.utils;

import com.barrage.api.constance.CookieConstance;
import org.springframework.lang.Nullable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author Tomshidi
 * @Date 2020年4月17日14:32:29
 * @Description
 * Cookie处理工具类
 */
public class CookieUtil {

    public static void setCookieNickName(HttpServletResponse response, String nickName) {
        Cookie cookie = new Cookie(CookieConstance.COOKIENAME, nickName);
        cookie.setMaxAge(CookieConstance.MAXAGE_MS);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static Map<String, Cookie> getCookieMap(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Map<String, Cookie> cookieMap = new HashMap<>();
        if (cookies == null || cookies.length == 0) {
            return null;
        }
        for (Cookie cookie : cookies) {
            cookieMap.put(cookie.getName(), cookie);
        }
        return cookieMap;
    }

    @Nullable
    public static List<Map.Entry<String,Cookie>> getAuthCookies(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = getCookieMap(request);
        if (cookieMap != null) {
            return cookieMap.entrySet().stream().
                    filter(item -> item.getKey().equals(CookieConstance.LOGIN_COOKIE_NAME)||item.getKey().equals(CookieConstance.COOKIE_EMAIL_NAME))
                    .collect(Collectors.toList());
        }else {
            return null;
        }
    }

    @Nullable
    public static Cookie getCookie(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = getCookieMap(request);
        if (cookieMap == null) {
            return null;
        }
        return cookieMap.get(name);
    }

    public static void setLoginCookie(HttpServletResponse response, String email, String value) {
        Cookie cookieLk = new Cookie(CookieConstance.LOGIN_COOKIE_NAME, value);
        Cookie cookieEmail = new Cookie(CookieConstance.COOKIE_EMAIL_NAME, email);
        cookieLk.setPath("/");
        cookieEmail.setPath("/");
        cookieLk.setMaxAge(CookieConstance.MAXAGE_MS);
        cookieEmail.setMaxAge(CookieConstance.MAXAGE_MS);
        response.addCookie(cookieLk);
        response.addCookie(cookieEmail);
    }
}
