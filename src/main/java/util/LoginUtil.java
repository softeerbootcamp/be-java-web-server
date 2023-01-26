package util;

import request.HttpRequest;
import db.SessionDb;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class LoginUtil {
    private final static Logger logger = LoggerFactory.getLogger(LoginUtil.class);

    public static boolean checkUserInfoMatch(HttpRequest httpRequest) {
        try {
            User user = UserDbUtil.findUserById(httpRequest.getParams().get("userId"));
            return Objects.equals(user.getPassword(), httpRequest.getParams().get("password"));
        } catch (NullPointerException e) {
            return false;
        }
    }

    public static User checkSession(HttpRequest httpRequest) throws NullPointerException {
        String cookie = httpRequest.getHttpRequestHeaders().get("Cookie");
        logger.debug("cookie: " + cookie);
        if (cookie == null) {
            throw new NullPointerException();
        }
        return SessionDb.getSession(cookie).getUser();
    }
}
