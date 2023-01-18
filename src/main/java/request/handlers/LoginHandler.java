package request.method.POST.handlers;

import file.FileContentType;
import model.Session;
import model.User;
import request.Request;
import request.RequestParser;
import response.HttpResponseStatus;
import response.Response;

import java.util.Map;
import java.util.UUID;

public class POSTLoginHandler extends POSTHandler {
    private static final POSTLoginHandler instance;

    static {
        instance = new POSTLoginHandler();
    }

    public static POSTLoginHandler getInstance() {
        return instance;
    }

    @Override
    public Response handle(Request request) {
        try {
            Map<String, String> requestBody = RequestParser.parseFormEncodedBody(request);
            User user = userService.findUser(requestBody.get("userId")).orElseThrow(()->{ throw new IllegalArgumentException(); });
            if (user.getPassword().equals(requestBody.get("password"))) {
                String sid = String.valueOf(UUID.randomUUID());
                sessionService.addSession(Session.of(sid, user));
                return Response.of(HttpResponseStatus.FOUND.getMessage().getBytes(), request.getResourceFileContentType(), HttpResponseStatus.FOUND,
                        "Set-Cookie: sid=" + sid + ";Path=/\r\n" +
                                "Location: /index.html\r\n");
            }
            throw new IllegalArgumentException();
        } catch (IllegalArgumentException e) {
            return Response.of("<script>alert('login failed'); window.location.href = 'http://localhost:8080/user/login_failed.html';</script>".getBytes(),
                    FileContentType.HTML.getContentType(), HttpResponseStatus.BAD_REQUEST);
        }
    }
}
