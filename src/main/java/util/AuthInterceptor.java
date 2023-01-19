package util;

import db.SessionStorage;
import exception.UserNotFoundException;
import model.UserSession;
import model.request.Request;

import java.util.Map;

import static util.HttpRequestUtils.parseSid;

public class AuthInterceptor {

    public static boolean isAuthUser(Request request) {
        Map<String, String> headers = request.getHeaders();
        if (headers.containsKey("Cookie")) {
            String cookie = headers.get("Cookie");
            return SessionStorage.isValidate(parseSid(cookie));
        }
        return false;
    }

    public static UserSession findUserSession(Request request) {
        if (isAuthUser(request)) {
            Map<String, String> headers = request.getHeaders();
            String cookie = headers.get("Cookie");
            return SessionStorage.findBySessionId(parseSid(cookie));
        }
        throw new UserNotFoundException();
    }
}
