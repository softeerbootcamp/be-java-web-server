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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class UserController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private static final String fileParentPath = "./src/main/resources/templates";

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Response getResponse(Request request) {
        if(request.getRequestLine().getUri().startsWith("/user/create")) return createUser(request);
        else if(request.getRequestLine().getUri().equals("/user/form.html")) return getUserFormHtml(request);

        return Response.from(Status.NOT_FOUND);
    }

    private Response createUser(Request request) {
        Map<String, String> userInfo = request.getRequestLine().parseQueryString();
        User user = User.from(userInfo.get("userId"), userInfo.get("password"),
                userInfo.get("name"), userInfo.get("email"));
        userService.signUp(user);

        Map<Header, String> headers = new HashMap<>();
        headers.put(Header.from("Location"), "/index.html");

        return Response.of(StatusLine.from(Status.FOUND), headers);
    }

    private Response getUserFormHtml(Request request) {
        Map<Header, String> headers = new HashMap<>();
        headers.put(Header.from("Content-Type"), "text/html;charset=utf-8");

        byte[] body = {};
        try {
            body = Files.readAllBytes(new File(fileParentPath + request.getRequestLine().getUri()).toPath());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        headers.put(Header.from("Content-Length"), Integer.toString(body.length));

        return Response.of(StatusLine.from(Status.OK), headers, body);
    }
}
