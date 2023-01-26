package request.handlers;

import file.FileContentType;
import model.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.Request;
import request.RequestHandler;
import request.RequestParser;
import response.HttpResponseStatus;
import response.Response;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;

public class QnAHandler implements RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(QnAHandler.class);

    private static final QnAHandler instance;

    static {
        instance = new QnAHandler();
    }

    private QnAHandler() {}

    public static QnAHandler getInstance() {
        return instance;
    }

    @Override
    public Response doGet(Request request) {
        try {
            if(!sessionService.isValid(request.getCookie())) {
                return Response.createFullResponse(
                        HttpResponseStatus.FOUND.getMessage().getBytes(),
                        request.getResourceFileContentType(),
                        HttpResponseStatus.FOUND,
                        "Location: /user/login.html\r\n");
            }
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
            return  Response.from(HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response doPost(Request request) {
        try {
            Map<String, String> requestBody = RequestParser.parseFormEncodedBody(request);
            String cookie = request.getCookie();
            String title = requestBody.get("title");
            String content = requestBody.get("content");

            if (sessionService.findSession(cookie).isEmpty()) {
                return Response.from(HttpResponseStatus.UNAUTHORIZED);
            }
            String userId = sessionService.findSession(cookie).get().getUserId();

            Post post = Post.of(0, title, content, userId, LocalDateTime.now());
            postService.addPost(post);

            return Response.createFullResponse(
                    HttpResponseStatus.FOUND.getMessage().getBytes(),
                    FileContentType.NO_MATCH.getContentType(),
                    HttpResponseStatus.FOUND,
                    "Location: /index.html\r\n");
        } catch (SQLException e) {
            logger.error(Arrays.toString(e.getStackTrace()));
            return  Response.from(HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
