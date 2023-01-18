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
import util.SessionUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
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
        else if(requestLine.getMethod().equals(Method.GET) &&
                requestLine.getUri().startsWith("/user/list")) return userListResponse(request);

        return Response.of(request, Status.NOT_FOUND);
    }

    private Response createUserResponse(Request request) {
        Map<String, String> userInfo = request.getBody().getContent();
        User user = User.of(userInfo.get("userId"), userInfo.get("password"),
                userInfo.get("name"), userInfo.get("email"));
        userService.signUp(user);

        Map<Header, String> headers = HeaderUtils.responseCreateUserHeader();

        return Response.of(StatusLine.of(request.getRequestLine().getHttpVersion(), Status.FOUND), headers);
    }

    private Response loginUserResponse(Request request) {
        Map<String, String> userLoginInfo = request.getBody().getContent();
        boolean isLoginSuccess = userService.logIn(userLoginInfo);

        Map<Header, String> headers;
        RequestLine requestLine = request.getRequestLine();
        if(isLoginSuccess) {
            String sessionId = SessionUtils.generateSessionId();
            Session session = Sessions.getSession(sessionId);
            session.setSessionData("user", userLoginInfo.get("userId"));

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
            byte[] body = getUserListHtml(userService.getUserList());
            // TODO: body에 빈 배열 넘어오는 경우 예외 처리
            headers = HeaderUtils.response200Header(ContentType.HTML, body.length);
            return Response.of(StatusLine.of(requestLine.getHttpVersion(), Status.OK), headers, body);
        }

        headers = HeaderUtils.responseRedirectLoginHtmlHeader();
        return Response.of(StatusLine.of(requestLine.getHttpVersion(), Status.FOUND), headers);
    }

    private byte[] getUserListHtml(Collection<User> users) {
        StringBuilder userList = new StringBuilder();
        int row = 0;

        for(User user : users) {
            userList.append("<tr><th scope=\"row\">")
                    .append(++row)
                    .append("</th><td>")
                    .append(user.getUserId()).append("</td><td>")
                    .append(user.getName())
                    .append("</td><td>")
                    .append(user.getEmail())
                    .append("</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td></tr>");
        }

        byte[] body;
        try {
            body = Files.readAllBytes(new File("./src/main/resources/templates/user/list.html").toPath());
        } catch(IOException e) {
            return new byte[0];
        }

        String originalListHtml = new String(body);
        String[] splitListHtml = originalListHtml.split("<tbody>");
        String resultListHtml = splitListHtml[0] + "<tbody>" + userList + splitListHtml[1];
        return resultListHtml.getBytes();
    }
}
