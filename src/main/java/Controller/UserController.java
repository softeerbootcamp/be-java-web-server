package Controller;

import exception.UrlNotFoundException;
import http.request.HttpMethod;
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

        if (path.startsWith("/create") && request.getMethod() == HttpMethod.POST) {
            String body = request.getBody();

            Map<String, String> userInfo = HttpRequestUtils.parseBodyMessage(body);

            UserService userService = new UserService();
            userService.signUp(userInfo);

            response.redirect(request, "/index.html");

            return "";
        }

        throw new UrlNotFoundException("잘못된 URL 요청입니다.");
    }
}
