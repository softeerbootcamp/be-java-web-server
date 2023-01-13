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

    public static final String PREFIX = "/user";
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Override
    public String process(HttpRequest request, HttpResponse response) {
        String url = request.getUrl();
        String path = url.split(PREFIX)[1];

        if (path.startsWith("/create")) {
            String queryString = HttpRequestUtils.getQueryString(path);

            Map<String, String> userInfo = HttpRequestUtils.parseQueryString(queryString);

            UserService userService = new UserService();
            userService.signUp(userInfo);

            response.setHttpStatusLine(request, HttpStatusCode.FOUND);
            response.addHttpHeader("Location", "/user/login.html");

            return "";
        }

        if (path.equals("/form.html")) {
            response.ok(request);
            return url;
        }

        if (path.equals("/list.html")) {
            response.ok(request);
            return url;
        }

        if (path.equals("/login.html")) {
            response.ok(request);
            return url;
        }

        if (path.equals("/login_failed.html")) {
            response.ok(request);
            return url;
        }

        if (path.equals("/profile.html")) {
            response.ok(request);
            return url;
        }

        // TODO 잘못된 URL이 들어올 경우 404 NOT_FOUND 응답
        throw new IllegalArgumentException();
    }
}
