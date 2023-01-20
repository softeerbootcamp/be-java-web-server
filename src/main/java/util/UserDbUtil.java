package util;

import Request.HttpRequest;
import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;

public class UserDbUtil {
    private static final Logger logger = LoggerFactory.getLogger(UserDbUtil.class);

    public static void saveUser(HttpRequest httpRequest) {
        Map<String, String> params = httpRequest.getParams();
        User user = new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
        Database.addUser(user);
        logger.debug(String.format("\nsave user: %s\n", user.toString()));
    }

    public static boolean alreadyExist(HttpRequest httpRequest) {
        String userId = httpRequest.getParams().get("userId");
        if (Objects.nonNull(findUserById(userId)))
            return true;
        return false;
    }

    public static User findUserById(String userId) {
        return Database.findUserById(userId);
    }
}
