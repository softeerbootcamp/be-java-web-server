package controller;

import model.domain.User;
import model.general.Header;
import model.general.Status;
import model.request.RequestLine;
import model.response.Response;
import model.response.StatusLine;
import service.UserService;

import java.util.HashMap;
import java.util.Map;

public class UserController implements Controller {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Response getResponse(RequestLine requestLine) {
        if(requestLine.getUri().startsWith("/user/create")) return createUser(requestLine);
        else return Response.from(Status.NOT_FOUND);
    }

    private Response createUser(RequestLine requestLine) {
        Map<String, String> userInfo = requestLine.parseQueryString();
        User user = User.from(userInfo.get("userId"), userInfo.get("password"),
                userInfo.get("name"), userInfo.get("email"));
        userService.signUp(user);

        Map<Header, String> headers = new HashMap<>();
        headers.put(Header.of("Location"), "/index.html");

        return Response.of(StatusLine.from(Status.FOUND), headers);
    }
}
