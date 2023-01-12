package Controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpStatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import util.HttpRequestUtils;

import java.util.Map;

public class UserController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Override
    public String process(HttpRequest request, HttpResponse response) {
        String url = request.getUrl();
        String path = url.split("/user")[1];

        if (path.startsWith("/create")) {
            String queryString = HttpRequestUtils.getQueryString(path);

            Map<String, String> userInfo = HttpRequestUtils.parseQueryString(queryString);

            UserService userService = new UserService();
            userService.signUp(userInfo);

            response.setHttpStatusLine(request, HttpStatusCode.FOUND);
            response.addHttpHeader("Location", "/user/login.html");

            return "";
        }

        // TODO 추후 다른 기능이 추가되면 수정
        response.ok(request);
        return url;
    }
}
