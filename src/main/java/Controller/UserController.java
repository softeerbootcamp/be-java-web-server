package Controller;

import exception.NullValueException;
import exception.UrlNotFoundException;
import exception.UserValidationException;
import http.request.HttpMethod;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import util.HttpRequestUtils;
import view.UserListView;
import webserver.session.SessionConst;
import webserver.session.SessionManager;

import java.util.Collection;
import java.util.Map;

public class UserController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    public static final String PREFIX = "/user";
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void process(HttpRequest request, HttpResponse response) {
        String url = request.getUrl();
        String path = url.split(PREFIX)[1];

        if (path.equals("/create") && request.getMethod() == HttpMethod.POST) {
            createUser(request, response);
            return;
        }

        if (path.equals("/login") && request.getMethod() == HttpMethod.POST) {
            login(request, response);
            return;
        }

        if (path.equals("/list") && request.getMethod() == HttpMethod.GET) {
            showUserList(request, response);
            return;
        }

        throw new UrlNotFoundException("잘못된 URL 요청입니다.");
    }

    private void createUser(HttpRequest request, HttpResponse response) {
        try {
            String body = request.getBody();

            Map<String, String> userInfo = HttpRequestUtils.parseBodyMessage(body);

            userService.signUp(userInfo);

            response.redirect(request, "/index.html");
        } catch (UserValidationException e) {
            logger.error(e.getMessage());
            response.redirect(request, "/user/form_failed.html");
        } catch (NullValueException e) {
            logger.error(e.getMessage());
            response.redirect(request, "/user/form.html");
        }
    }

    private void login(HttpRequest request, HttpResponse response) {
        try {
            String body = request.getBody();
            Map<String, String> userInfo = HttpRequestUtils.parseBodyMessage(body);

            User loginUser = userService.login(userInfo);

            String sessionId = SessionManager.createSession(loginUser.getUserId());
            logger.debug("sessionId: {}", sessionId);
            response.addHttpHeader("Set-Cookie", SessionConst.SESSION_COOKIE_NAME + "=" + sessionId + "; Path=/");

            response.redirect(request, "/index.html");
        } catch (UserValidationException e) {
            response.redirect(request, "/user/login_failed.html");
        } catch (NullValueException e) {
            response.redirect(request, "/user/login.html");
        }
    }

    private void showUserList(HttpRequest request, HttpResponse response) {
        if (!request.isLogin()) {
            response.redirect(request, "/user/login.html");
            return;
        }

        Collection<User> users = userService.findUserList();
        String body = UserListView.render(users);

        response.ok(request);
        response.setBodyMessage(body);
    }
}
