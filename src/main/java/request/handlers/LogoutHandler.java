package request.handlers;

import file.FileContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.Request;
import request.RequestHandler;
import response.HttpResponseStatus;
import response.Response;

import java.sql.SQLException;
import java.util.Arrays;

public class LogoutHandler implements RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(LogoutHandler.class);

    private final static LogoutHandler instance;

    static {
        instance = new LogoutHandler();
    }

    private LogoutHandler() {}

    public static LogoutHandler getInstance() {
        return instance;
    }

    @Override
    public Response doGet(Request request) {
        try {
            String cookie_sid = request.getCookie();
            if (sessionService.isValid(cookie_sid)) {
                sessionService.removeSession(cookie_sid);
            }
            return Response.createFullResponse(
                    HttpResponseStatus.FOUND.getMessage().getBytes(),
                    FileContentType.NO_MATCH.getContentType(),
                    HttpResponseStatus.FOUND,
                    "Location: /index.html\r\n" + "Set-Cookie: sid=;Path=/\r\n"
            );
        } catch (SQLException e) {
            logger.error(Arrays.toString(e.getStackTrace()));
            return Response.from(HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
