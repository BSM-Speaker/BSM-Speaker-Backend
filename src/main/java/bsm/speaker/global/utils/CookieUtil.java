package bsm.speaker.global.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class CookieUtil {

    @Value("${env.cookie.domain}")
    private String COOKIE_DOMAIN;
    @Value("${env.cookie.secure}")
    private boolean COOKIE_SECURE;

    public Cookie createCookie(String name, String value, long time) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(COOKIE_SECURE);
        cookie.setMaxAge((int) time);
        cookie.setPath("/");
        cookie.setDomain(COOKIE_DOMAIN);
        return cookie;
    }

    public Cookie getCookie(HttpServletRequest req, String name) {
        final Cookie[] cookies = req.getCookies();
        if (cookies == null) return null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie;
            }
        }
        return null;
    }
}
