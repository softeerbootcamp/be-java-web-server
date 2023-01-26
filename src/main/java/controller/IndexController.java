package controller;

import exception.FileNotFoundException;
import exception.NonLogInException;
import http.ContentType;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpStatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.BoardService;
import service.SessionService;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class IndexController extends AbstractController {

    private static final String INDEX_PATH = "/index.html";
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);


    private final SessionService sessionService;
    private final BoardService boardService;

    public IndexController(SessionService sessionService, BoardService boardService) {
        this.sessionService = sessionService;
        this.boardService = boardService;
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
            byte[] boardBody = FileIoUtils.makeBoardList(boardService.findAll(), body);

            httpResponse.forward(HttpStatusCode.OK, contentType, boardBody);

        } catch (NonLogInException | RuntimeException e) {
            byte[] body = FileIoUtils.loadFile(filePath);
            byte[] boardBody = FileIoUtils.makeBoardList(boardService.findAll(), body);

            httpResponse.forward(HttpStatusCode.OK, contentType, boardBody);

        } catch (FileNotFoundException e) {
            byte[] body = FileIoUtils.load404ErrorFile();
            httpResponse.do404(body);
            throw new RuntimeException(e);
        }
    }
}
