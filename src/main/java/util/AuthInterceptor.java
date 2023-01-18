package util;

import db.SessionStorage;
import exception.UserNotFoundException;
import model.User;
import model.request.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static util.HttpRequestUtils.parseSid;

public class AuthInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    public static boolean isAuthUser(Request request) {
        Map<String, String> headers = request.getHeaders();
        if (headers.containsKey("Cookie")) {
            String cookie = headers.get("Cookie");
            return SessionStorage.isValidate(parseSid(cookie));
        }
        return false;
    }

    public static User findUser(Request request) {
        if (isAuthUser(request)) {
            Map<String, String> headers = request.getHeaders();
            String cookie = headers.get("Cookie");
            return SessionStorage.findBySessionId(parseSid(cookie)).orElseThrow(UserNotFoundException::new);
        }
        throw new UserNotFoundException();
    }
}
