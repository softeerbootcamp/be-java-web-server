package service;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

import java.util.Map;

public class LogInService {
    private static final Logger logger = LoggerFactory.getLogger(SignUpService.class);

    public static User makeUserInfo(String uri) {
        Map<String, String> params = HttpRequestUtils.parseQueryString(uri);

        User user = new User(
                params.get("userId"),
                params.get("password"),
                params.get("name"),
                params.get("email"));

        logger.debug("User : {}", user);

        return user;
    }
}
