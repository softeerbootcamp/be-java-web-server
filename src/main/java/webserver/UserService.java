package webserver;

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

        return "/user/login.html";
    }
}
