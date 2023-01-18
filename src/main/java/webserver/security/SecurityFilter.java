package webserver.security;

import db.CookieDataBase;
import model.HttpCookie;
import org.checkerframework.checker.units.qual.A;
import webserver.domain.ContentType;
import webserver.domain.StatusCodes;
import webserver.domain.request.Request;
import webserver.domain.response.Response;
import webserver.exception.HttpRequestException;
import webserver.utils.HttpCookieUtils;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SecurityFilter {

    private static List<String> accessList;

    static{
        accessList = List.of("/user/list");
    }

    public static void checkAuthorization(String path , String sessionId){
        if(accessList.stream().anyMatch(item-> path.startsWith(item))){
            HttpCookie cookie = CookieDataBase.findCookieById(sessionId).orElse(null);
            if(cookie == null || !cookie.isValid()) {
                throw new HttpRequestException(StatusCodes.OK, "<script>alert('권한이 없습니다.'); window.location.href = 'http://localhost:8080/index.html';</script>");
            }
        }
    }
}
