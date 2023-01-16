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

        return Response.of(request, Status.NOT_FOUND);
    }

    private Response createUserResponse(Request request) {
        Map<String, String> userInfo = request.getBody().getContent();
        User user = User.of(userInfo.get("userId"), userInfo.get("password"),
                userInfo.get("name"), userInfo.get("email"));
        userService.signUp(user);

        Map<Header, String> headers = response302Header();

        return Response.of(StatusLine.of(request.getRequestLine().getHttpVersion(), Status.FOUND), headers);
    }

    private Map<Header, String> response302Header() {
        Map<Header, String> headers = new HashMap<>();
        headers.put(Header.from("Location"), "/index.html");

        return headers;
    }
}
