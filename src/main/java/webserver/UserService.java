package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

import java.util.Map;

public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public String createUser(String url) {
        String[] splited = url.split("\\?");
        String queryString = splited[1];
        logger.debug("userInfo : {}" , queryString);

        Map<String, String> userInfo = HttpRequestUtils.parseQueryString(queryString);

        return "/user/form.html";
    }
}
