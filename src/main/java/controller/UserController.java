package controller;

import model.domain.User;
import model.general.ContentType;
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
import util.HeaderUtils;
import util.ResponseBodyUtils;
import util.SessionUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
        Method requestMethod = requestLine.getMethod();
        String requestUri = requestLine.getUri();

        if(requestMethod.equals(Method.POST) &&
                requestUri.startsWith("/user/create")) return createUserResponse(request);
        else if(requestMethod.equals(Method.POST) &&
                requestUri.startsWith("/user/login")) return loginUserResponse(request);
        else if(requestMethod.equals(Method.GET) &&
                requestUri.startsWith("/user/list")) return userListResponse(request);

        return Response.from(StatusLine.of(requestLine.getHttpVersion(), Status.NOT_FOUND));
    }

    private Response createUserResponse(Request request) {
        Map<String, String> userInfo = request.getBody().getContent();
        User user = User.of(userInfo.get("userId"), userInfo.get("password"),
                userInfo.get("name"), userInfo.get("email"));
        userService.signUp(user);

        Map<Header, String> headers = HeaderUtils.responseRedirectIndexHtmlHeader();

        return Response.of(StatusLine.of(request.getRequestLine().getHttpVersion(), Status.FOUND), headers);
    }

    private Response loginUserResponse(Request request) {
        Map<String, String> userLoginInfo = request.getBody().getContent();
        boolean isLoginSuccess = userService.logIn(userLoginInfo);

        Map<Header, String> headers;
        RequestLine requestLine = request.getRequestLine();
        if(isLoginSuccess) {
            User user = userService.getUser(userLoginInfo.get("userId"));
            String sessionId = SessionUtils.generateSessionId();
            Session session = Sessions.getSession(sessionId);
            session.setSessionData("user", user);

            headers = HeaderUtils.responseLoginSuccessHeader(sessionId);

            return Response.of(StatusLine.of(requestLine.getHttpVersion(), Status.FOUND), headers);
        }

        headers = HeaderUtils.responseLoginFailHeader();
        return Response.of(StatusLine.of(requestLine.getHttpVersion(), Status.FOUND), headers);
    }

    private Response userListResponse(Request request) {
        Map<Header, String> headers;
        RequestLine requestLine = request.getRequestLine();

        if(Sessions.isExistSession(request.getSessionId())) {
            byte[] body;
            try {
                body = Files.readAllBytes(new File("./src/main/resources/templates/user/list.html").toPath());
            } catch(IOException e) {
                return Response.from(StatusLine.of(requestLine.getHttpVersion(), Status.NOT_FOUND));
            }
            body = ResponseBodyUtils.getUserListHtmlWhenLogin(body, request, userService.getUserList());

            headers = HeaderUtils.response200Header(ContentType.HTML, body.length);

            return Response.of(StatusLine.of(requestLine.getHttpVersion(), Status.OK), headers, body);
        }

        headers = HeaderUtils.responseRedirectLoginHtmlHeader();
        return Response.of(StatusLine.of(requestLine.getHttpVersion(), Status.FOUND), headers);
    }
}
