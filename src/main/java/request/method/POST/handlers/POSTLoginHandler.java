package request.method.POST.handlers;

import db.Database;
import file.FileContentType;
import model.User;
import request.Request;
import request.RequestParser;
import response.HttpResponseStatus;
import response.Response;
import session.Session;

import java.util.Map;
import java.util.UUID;

public class POSTLoginHandler implements POSTHandler {
    private static final POSTLoginHandler POST_LOGIN_HANDLER;

    static {
        POST_LOGIN_HANDLER = new POSTLoginHandler();
    }

    public static POSTLoginHandler getInstance() {
        return POST_LOGIN_HANDLER;
    }

    @Override
    public Response handle(Request request) {
        try {
            Map<String, String> requestBody = RequestParser.parseBody(request);
            User user = Database.findUserById(requestBody.get("userId"));
            if (user.getPassword().equals(requestBody.get("password"))) {
                String uuid = String.valueOf(UUID.randomUUID());
                Session.add(uuid, user);
                return Response.of(HttpResponseStatus.FOUND.getMessage().getBytes(), request.getResourceFileContentType(), HttpResponseStatus.FOUND,
                        "Set-Cookie: sid=" + uuid + ";Path=/\r\n" +
                                "Location: /index.html\r\n");
            }
            throw new IllegalArgumentException();
        } catch (IllegalArgumentException e) {
            return Response.of("<script>alert('login failed'); window.location.href = 'http://localhost:8080/user/login_failed.html';</script>".getBytes(),
                    FileContentType.HTML.getContentType(), HttpResponseStatus.UNAUTHORIZED);
        }
    }
}
