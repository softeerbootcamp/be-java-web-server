package controller;

import http.ContentType;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpStatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.SessionService;
import service.UserService;
import utils.FileIoUtils;

import java.io.IOException;

public class IndexController extends AbstractController {

    private static final String INDEX_PATH = "/index.html";
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);


    private final SessionService sessionService;
    private final UserService userService;

    public IndexController(SessionService sessionService, UserService userService) {
        this.sessionService = sessionService;
        this.userService = userService;
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        ContentType contentType = ContentType.from(INDEX_PATH);
        String filePath = contentType.getDirectory() + INDEX_PATH;

        try {
            String sessionId = httpRequest.getSessionId();
            logger.info(sessionId);

            sessionService.validateHasSession(sessionId);
            logger.info("check");

            logger.info(sessionService.getUserId(sessionId));
            byte[] body = userService.makeUserNameIndexBody(sessionService.getUserId(sessionId), filePath);
            httpResponse.forward(HttpStatusCode.OK, contentType, body);

        } catch (NullPointerException e) {

            byte[] body = FileIoUtils.activeLoginBtn(filePath);
            httpResponse.forward(HttpStatusCode.OK, contentType, body);
        }
    }

}
