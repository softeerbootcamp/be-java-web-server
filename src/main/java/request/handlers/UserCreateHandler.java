package request.handlers;

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
import java.util.Map;

public class UserRegisterHandler implements RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(UserRegisterHandler.class);

    private final static UserRegisterHandler instance;

    static {
        instance = new UserRegisterHandler();
    }

    private UserRegisterHandler() {}

    public static UserRegisterHandler getInstance() {
        return instance;
    }

    @Override
    public Response doGet(Request request) {
        try {
            byte[] file = generateDynamicHeader(request.getCookie(), Files.readAllBytes(new File("src/main/resources/templates" + request.getResource()).toPath()));
            return Response.createSimpleResponse(file, request.getResourceFileContentType(), HttpResponseStatus.OK);
        } catch (IOException e) {
            return Response.from(HttpResponseStatus.NOT_FOUND);
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
        }
    }
}
