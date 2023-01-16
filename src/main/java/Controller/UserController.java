package Controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import service.UserService;
import util.HttpRequestUtils;

import java.util.Map;

public class UserController implements Controller {

    public static final String PREFIX = "/user";

    @Override
    public String process(HttpRequest request, HttpResponse response) {
        String url = request.getUrl();
        String path = url.split(PREFIX)[1];

        if (path.startsWith("/create")) {
            String queryString = HttpRequestUtils.getQueryString(path);

            Map<String, String> userInfo = HttpRequestUtils.parseQueryString(queryString);

            UserService userService = new UserService();
            userService.signUp(userInfo);

            response.redirect(request, "/user/login.html");

            return "";
        }

        // TODO 잘못된 URL이 들어올 경우 404 NOT_FOUND 응답
        throw new IllegalArgumentException();
    }
}
