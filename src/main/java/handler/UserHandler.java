package handler;

import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import util.HttpRequestUtils;

import java.util.Map;

public class UserHandler implements Handler {

    private static final Logger logger = LoggerFactory.getLogger(UserHandler.class);

    @Override
    public String handle(HttpRequest request, HttpResponse response) {
        String url = request.getUrl();
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
