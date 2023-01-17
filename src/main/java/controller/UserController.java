package controller;

import model.domain.User;
import model.general.Header;
import model.general.Method;
import model.general.Status;
import model.request.Request;
import model.request.RequestLine;
import model.response.Response;
import model.response.StatusLine;
import model.session.Session;
import model.session.Sessions;
import service.UserService;
import util.SessionUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class UserController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Response getResponse(Request request) {
        RequestLine requestLine = request.getRequestLine();

        if(requestLine.getMethod().equals(Method.POST) &&
                requestLine.getUri().startsWith("/user/create")) return createUserResponse(request);
        else if(requestLine.getMethod().equals(Method.POST) &&
                requestLine.getUri().startsWith("/user/login")) return loginUserResponse(request);

        return Response.of(request, Status.NOT_FOUND);
    }

    private Response createUserResponse(Request request) {
        Map<String, String> userInfo = request.getBody().getContent();
        User user = User.of(userInfo.get("userId"), userInfo.get("password"),
                userInfo.get("name"), userInfo.get("email"));
        userService.signUp(user);

        Map<Header, String> headers = responseCreateUserHeader();

        return Response.of(StatusLine.of(request.getRequestLine().getHttpVersion(), Status.FOUND), headers);
    }

    private Response loginUserResponse(Request request) {
        Map<String, String> userLoginInfo = request.getBody().getContent();
        User user = User.of(userLoginInfo.get("userId"), userLoginInfo.get("password"),
                userLoginInfo.get("name"), userLoginInfo.get("email"));
        boolean isLoginSuccess = userService.logIn(user);

        Map<Header, String> headers;
        RequestLine requestLine = request.getRequestLine();
        if(isLoginSuccess) {
            headers = responseLoginSuccessHeader();
            Session session = Sessions.getSession(parseSessionIdFromHeaders(headers));
            session.setSessionData("user", user);

            return Response.of(StatusLine.of(requestLine.getHttpVersion(), Status.FOUND), headers);
        }

        headers = responseLoginFailHeader();
        return Response.of(StatusLine.of(requestLine.getHttpVersion(), Status.FOUND), headers);
    }

    private Map<Header, String> responseCreateUserHeader() {
        Map<Header, String> headers = new HashMap<>();
        headers.put(Header.from("Location"), "/index.html");

        return headers;
    }

    private Map<Header, String> responseLoginSuccessHeader() {
        Map<Header, String> headers = new HashMap<>();
        headers.put(Header.from("Location"), "/index.html");
        headers.put(Header.from("Set-Cookie"), "sid=" + SessionUtils.generateSessionId() + "; Path=/");

        return headers;
    }

    private Map<Header, String> responseLoginFailHeader() {
        Map<Header, String> headers = new HashMap<>();
        headers.put(Header.from("Location"), " /user/login_failed.html");

        return headers;
    }

    private String parseSessionIdFromHeaders(Map<Header, String> headers) {
        String cookie = headers.get(Header.SET_COOKIE);
        String sessionId = cookie.split(" ")[0].split("=")[1];

        return sessionId.substring(0, sessionId.length() - 1);
    }
}
