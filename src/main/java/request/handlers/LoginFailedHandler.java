package request.handlers;

import file.FileContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.Request;
import request.RequestHandler;
import response.HttpResponseStatus;
import response.Response;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

public class LoginFailedHandler implements RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(LoginFailedHandler.class);

    private static final LoginFailedHandler instance;

    static {
        instance = new LoginFailedHandler();
    }

    private LoginFailedHandler() {}

    public static LoginFailedHandler getInstance() {
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
}
