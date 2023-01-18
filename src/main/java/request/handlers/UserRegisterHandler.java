package request.method.POST.handlers;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.Request;
import request.RequestParser;
import response.HttpResponseStatus;
import response.Response;

import java.util.Map;

public class POSTUserRegisterHandler extends POSTHandler{
    private static final Logger logger = LoggerFactory.getLogger(POSTUserRegisterHandler.class);

    private final static POSTUserRegisterHandler instance;

    static {
        instance = new POSTUserRegisterHandler();
    }

    public static POSTHandler getInstance() {
        return instance;
    }

    @Override
    public Response handle(Request request) {
        try {
            Map<String, String> requestBody = RequestParser.parseFormEncodedBody(request);
            User user = new User(requestBody);
            userService.addUser(user);
            logger.debug("saved: {}", user);
            return Response.of(HttpResponseStatus.FOUND.getMessage().getBytes(), request.getResourceFileContentType(), HttpResponseStatus.FOUND,
                    "Location: /index.html\r\n");
        } catch (IllegalArgumentException e) {
            logger.error("잘못된 입력값");
            return Response.of(HttpResponseStatus.BAD_REQUEST);
        }
    }
}
