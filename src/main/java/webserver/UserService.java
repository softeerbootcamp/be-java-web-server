package webserver;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

import java.util.Map;

public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public String signUp(String requestLine) {
        String[] splited = requestLine.split("\\?");
        String queryString = splited[1];
        logger.debug("userInfo : {}" , queryString);

        Map<String, String> userInfo = HttpRequestUtils.parseQueryString(queryString);

        User user = createUser(userInfo);
        logger.info("user : {}", user);

        Database.addUser(user);

        return "/user/login.html";
    }

    private User createUser(Map<String, String> userInfo) {
        String userId = userInfo.get("userId");
        String password = userInfo.get("password");
        String name = userInfo.get("name");
        String email = userInfo.get("email");

        return new User(userId, password, name, email);
    }
}
