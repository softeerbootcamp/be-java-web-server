package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

import java.util.Map;

public class UserRequestHandler {

    private static final Logger logger = LoggerFactory.getLogger(UserRequestHandler.class);

    public String handle(String url) {
        if (url.contains("/create")) {
            String queryString = HttpRequestUtils.getQueryString(url);

            Map<String, String> userInfo = HttpRequestUtils.parseQueryString(queryString);

            UserService userService = new UserService();
            userService.signUp(userInfo);

            return "/user/login.html";
        }

        // TODO 추후 다른 기능이 추가되면 수정할 예정
        return url;
    }
}
