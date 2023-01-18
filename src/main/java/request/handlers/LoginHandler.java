package request.handlers;

import file.FileContentType;
import model.Session;
import model.User;
import request.Request;
import request.RequestHandler;
import request.RequestParser;
import response.HttpResponseStatus;
import response.Response;

import java.util.Map;
import java.util.UUID;

public class LoginHandler implements RequestHandler {
    private static final LoginHandler instance;

    static {
        instance = new LoginHandler();
    }

    private LoginHandler() {}

    public static LoginHandler getInstance() {
        return instance;
    }

    @Override
    public Response doPost(Request request) {
        try {
            Map<String, String> requestBody = RequestParser.parseFormEncodedBody(request);
            User user = userService.findUser(requestBody.get("userId")).orElseThrow(()->{ throw new IllegalArgumentException(); });
            if (user.getPassword().equals(requestBody.get("password"))) {
                String sid = String.valueOf(UUID.randomUUID());
                sessionService.addSession(Session.of(sid, user));
                return Response.createFullResponse(HttpResponseStatus.FOUND.getMessage().getBytes(), request.getResourceFileContentType(), HttpResponseStatus.FOUND,
                        "Set-Cookie: sid=" + sid + ";Path=/\r\n" +
                                "Location: /index.html\r\n");
            }
            throw new IllegalArgumentException();
        } catch (IllegalArgumentException e) {
            return Response.createSimpleResponse("<script>alert('login failed'); window.location.href = 'http://localhost:8080/user/login_failed.html';</script>".getBytes(),
                    FileContentType.HTML.getContentType(), HttpResponseStatus.BAD_REQUEST);
        }
    }
}
