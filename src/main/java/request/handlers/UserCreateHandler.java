package request.handlers;

import file.FileContentType;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.Request;
import request.RequestHandler;
import request.RequestParser;
import response.HttpResponseStatus;
import response.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;

public class UserCreateHandler implements RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(UserCreateHandler.class);

    private final static UserCreateHandler instance;

    static {
        instance = new UserCreateHandler();
    }

    private UserCreateHandler() {}

    public static UserCreateHandler getInstance() {
        return instance;
    }

    // TODO: user/create.html 수정
    @Override
    public Response doGet(Request request) {
        try {
            String resource = request.getResource();
            if(!resource.endsWith(".html")) {
                resource += ".html";
            }
            byte[] file = generateDynamicHeader(request.getCookie(),
                    Files.readAllBytes(new File("src/main/resources/templates" + resource).toPath()));
            return Response.createSimpleResponse(file, FileContentType.HTML.getContentType(), HttpResponseStatus.OK);
        } catch (IOException e) {
            return Response.from(HttpResponseStatus.NOT_FOUND);
        } catch (SQLException | NullPointerException e) {
            return Response.from(HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response doPost(Request request) {
        try {
            Map<String, String> requestBody = RequestParser.parseFormEncodedBody(request);
            User user = User.from(requestBody);
            userService.addUser(user);
            logger.debug("saved: {}", user);
            return Response.createFullResponse(HttpResponseStatus.FOUND.getMessage().getBytes(), request.getResourceFileContentType(), HttpResponseStatus.FOUND,
                    "Location: /index.html\r\n");
        } catch (IllegalArgumentException e) {
            logger.error("잘못된 입력값");
            return Response.from(HttpResponseStatus.BAD_REQUEST);
        } catch (SQLException e) {
            logger.error(Arrays.toString(e.getStackTrace()));
            return Response.from(HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
