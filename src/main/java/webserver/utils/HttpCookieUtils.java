package webserver.utils;

import db.CookieDataBase;
import model.HttpCookie;
import webserver.domain.StatusCodes;
import webserver.exception.HttpRequestException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

public class HttpCookieUtils {



    public static HttpCookie generateCookie(String userid){

        String cookieStr = UUID.randomUUID().toString();
        HttpCookie cookie = new HttpCookie(cookieStr,userid,LocalDateTime.now());
        CookieDataBase.addCookie(cookie);
        return cookie;
        }

    public static Optional<HttpCookie> cookieValidation (String sessionId){
        HttpCookie cookie = CookieDataBase.findCookieById(sessionId).orElse(null);
        if(cookie != null){
            LocalDateTime now = LocalDateTime.now();
            if(cookie.isValid(now))  //available session
                cookie.updateCookieTimeInfo(now);  //renew lastAccessTime, expireDate, and maxAge of the session
            else   //expired session
                cookieInvalidation(sessionId);
        }
        return Optional.of(cookie);
    }


    public static String cookieInvalidation(String sessionId){
        StringBuilder newSession = new StringBuilder();
        CookieDataBase.findCookieById(sessionId).ifPresent(cookie -> {
            cookie.invalidateCookie();
            newSession.append(cookie.toString());
        });
        return newSession.toString();
    }
}
