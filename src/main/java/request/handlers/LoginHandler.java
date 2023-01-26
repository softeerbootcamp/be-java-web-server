package request.handlers;

import file.FileContentType;
import model.Session;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.Request;
import request.RequestHandler;
import request.RequestParser;
import response.HttpResponseStatus;
import response.Response;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class LoginHandler implements RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(LoginHandler.class);

    private static final LoginHandler instance;

    static {
        instance = new LoginHandler();
    }

    private LoginHandler() {}

    public static LoginHandler getInstance() {
        return instance;
    }

    @Override
    public Response doGet(Request request) {
        try {
            String resource = request.getResource();
            if(!resource.endsWith(".html")) {
                resource += ".html";
            }
            String filePath = "src/main/resources/templates" + resource;
            String content = generateDynamicHeader(request.getCookie(), filePath);
            return Response.createSimpleResponse(content.getBytes(), FileContentType.HTML.getContentType(), HttpResponseStatus.OK);
        } catch (IOException e) {
            return Response.from(HttpResponseStatus.NOT_FOUND);
        } catch (SQLException e) {
            logger.error(Arrays.toString(e.getStackTrace()));
            return Response.from(HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response doPost(Request request) {
        try {
            Map<String, String> requestBody = RequestParser.parseFormEncodedBody(request);
            User user = userService.findUserByIdAndPwd(requestBody.get("userId"), requestBody.get("password")).orElseThrow(()->{ throw new IllegalArgumentException(); });
            String sid = String.valueOf(UUID.randomUUID());
            sessionService.addSession(Session.of(sid, user.getId()));
            return Response.createFullResponse(HttpResponseStatus.FOUND.getMessage().getBytes(), request.getResourceFileContentType(), HttpResponseStatus.FOUND,
                    "Set-Cookie: sid=" + sid + ";Path=/\r\n" +
                            "Location: /index.html\r\n");
        } catch (IllegalArgumentException e) {
            return Response.createSimpleResponse("<script>alert('login failed'); window.location.href = 'http://localhost:8080/user/login_failed.html';</script>".getBytes(),
                    FileContentType.HTML.getContentType(), HttpResponseStatus.BAD_REQUEST);
        } catch (SQLException e) {
            logger.error(Arrays.toString(e.getStackTrace()));
            return Response.createSimpleResponse("<script>alert('login failed'); window.location.href = 'http://localhost:8080/user/login_failed.html';</script>".getBytes(),
                    FileContentType.HTML.getContentType(), HttpResponseStatus.BAD_REQUEST);
        }
    }
}
