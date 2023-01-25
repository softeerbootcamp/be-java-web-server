package controller;

import exception.FileNotFoundException;
import exception.NonLogInException;
import http.ContentType;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpStatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.SessionService;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class IndexController extends AbstractController {

    private static final String INDEX_PATH = "/index.html";
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);


    private final SessionService sessionService;

    public IndexController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, FileNotFoundException, URISyntaxException {
        ContentType contentType = ContentType.from(INDEX_PATH);
        String filePath = contentType.getDirectory() + INDEX_PATH;

        try {
            String sessionId = httpRequest.getSessionId();
            sessionService.validateHasSession(sessionId);
            logger.info(sessionId);

            byte[] body = FileIoUtils.replaceLoginBtnToUserName(sessionService.getUserName(sessionId), filePath);
            httpResponse.forward(HttpStatusCode.OK, contentType, body);

        } catch (NonLogInException e) {
            byte[] body = FileIoUtils.loadFile(filePath);
            httpResponse.forward(HttpStatusCode.OK, contentType, body);

        } catch (FileNotFoundException e) {
            byte[] body = FileIoUtils.load404ErrorFile();
            httpResponse.do404(body);
            throw new RuntimeException(e);
        }
    }

}
