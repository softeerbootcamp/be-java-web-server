package controller;

import model.domain.User;
import model.general.Header;
import model.general.Status;
import model.request.Request;
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
        if(request.getRequestLine().getUri().startsWith("/user/create")) return createUser(request);

        return Response.from(Status.NOT_FOUND);
    }

    private Response createUser(Request request) {
        Map<String, String> userInfo = request.getRequestLine().parseQueryString();
        User user = User.of(userInfo.get("userId"), userInfo.get("password"),
                userInfo.get("name"), userInfo.get("email"));
        userService.signUp(user);

        Map<Header, String> headers = new HashMap<>();
        headers.put(Header.from("Location"), "/index.html");

        return Response.of(StatusLine.from(Status.FOUND), headers);
    }
}
