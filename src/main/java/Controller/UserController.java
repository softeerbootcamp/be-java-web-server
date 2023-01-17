package Controller;

import exception.NullValueException;
import exception.UrlNotFoundException;
import exception.UserValidationException;
import http.request.HttpMethod;
import http.request.HttpRequest;
import http.response.HttpResponse;
import service.UserService;
import util.HttpRequestUtils;

import java.util.Map;

public class UserController implements Controller {

    public static final String PREFIX = "/user";
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String process(HttpRequest request, HttpResponse response) {
        String url = request.getUrl();
        String path = url.split(PREFIX)[1];

        if (path.startsWith("/create") && request.getMethod() == HttpMethod.POST) {
            try {
                String body = request.getBody();

                Map<String, String> userInfo = HttpRequestUtils.parseBodyMessage(body);

                userService.signUp(userInfo);

                response.redirect(request, "/index.html");

                return "";
            } catch (NullValueException | UserValidationException e) {
                response.redirect(request, "/user/form_failed.html");
                return "";
            }
        }

        throw new UrlNotFoundException("잘못된 URL 요청입니다.");
    }
}
