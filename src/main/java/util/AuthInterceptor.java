package util;

import db.SessionStorage;
import model.User;
import model.request.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static util.HttpRequestUtils.parseSid;

public class AuthInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    //TODO 리팩토링 필요
    public static boolean isAuthUser(Request request) {
        try {
            Map<String, String> headers = request.getHeaders();
            if (headers.containsKey("Cookie")) {
                String cookie = headers.get("Cookie");
                logger.debug(">> 쿠키 있어요! {}", cookie);
                User user = SessionStorage.findBySessionId(parseSid(cookie)).orElseThrow(IllegalArgumentException::new);
                logger.debug(">> user id : {}", user.getUserId());
                return true;
            }
            logger.error(">> 쿠키에 들어있는 세션 값에 일치하는 유저 없음");
            return false;
        } catch (IllegalArgumentException e) {
            logger.error(">> 쿠키에 들어있는 세션 값에 일치하는 유저 없음");
            return false;
        }
    }
}
