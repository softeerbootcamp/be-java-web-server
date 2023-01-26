package controller;

import exception.NonLogInException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpStatusCode;
import service.BoardService;
import service.SessionService;

import java.io.IOException;

public class BoardCreateController extends AbstractController {

    private final SessionService sessionService;
    private final BoardService boardService;

    public BoardCreateController(SessionService sessionService, BoardService boardService) {
        this.sessionService = sessionService;
        this.boardService = boardService;
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        try {
            sessionService.validateHasSession(httpRequest.getSessionId());
            boardService.addBoard(httpRequest.getParameters());
            httpResponse.sendRedirect(HttpStatusCode.FOUND, "/index.html");

        } catch (NonLogInException e) {
            httpResponse.sendRedirect(HttpStatusCode.FOUND, "/user/login.html");
        }
    }
}
