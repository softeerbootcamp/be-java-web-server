package controller;

import model.domain.User;
import model.general.Header;
import model.general.Method;
import model.general.Status;
import model.request.Request;
import model.request.RequestLine;
import model.response.Response;
import model.response.StatusLine;
import service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class UserController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Response getResponse(Request request) {
        RequestLine requestLine = request.getRequestLine();

        if(requestLine.getMethod().equals(Method.from("POST")) &&
                requestLine.getUri().startsWith("/user/create")) return createUserResponse(request);
        else if(requestLine.getMethod().equals(Method.from("POST")) &&
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
        boolean isLoginSuccess = userService.logIn(userLoginInfo);

        Map<Header, String> headers;
        if(isLoginSuccess) {
            headers = responseLoginSuccessHeader();
            return Response.of(StatusLine.of(request.getRequestLine().getHttpVersion(), Status.FOUND), headers);
        }

        headers = responseLoginFailHeader();
        return Response.of(StatusLine.of(request.getRequestLine().getHttpVersion(), Status.FOUND), headers);
    }

    private Map<Header, String> responseCreateUserHeader() {
        Map<Header, String> headers = new HashMap<>();
        headers.put(Header.from("Location"), "/index.html");

        return headers;
    }

    private Map<Header, String> responseLoginSuccessHeader() {
        Map<Header, String> headers = new HashMap<>();
        headers.put(Header.from("Location"), "/index.html");
        headers.put(Header.from("Set-Cookie"), "sid=" + generateRandomId() + "; Path=/");

        return headers;
    }

    private Map<Header, String> responseLoginFailHeader() {
        Map<Header, String> headers = new HashMap<>();
        headers.put(Header.from("Location"), " /user/login_failed.html");

        return headers;
    }

    private String generateRandomId() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 19;
        Random random = new Random();

        return random.ints(leftLimit,rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
