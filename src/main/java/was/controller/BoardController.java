package was.controller;

import db.BoardDatabase;
import db.SessionStorage;
import enums.HttpMethod;
import model.Board;
import service.BoardService;
import util.HttpParser;
import was.annotation.Auth;
import was.annotation.RequestMapping;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;

public class BoardController implements Controller {
    private static BoardController boardController;
    private BoardService boardService = BoardService.getInstance();
    private BoardController() {
    }
    public static BoardController getInstance() {
        if (boardController == null) {
            synchronized (BoardController.class) {
                return new BoardController();
            }
        }
        return boardController;
    }
    @Auth
    @RequestMapping(method = HttpMethod.POST, value = "/boards")
    public HttpResponse insertBoards(HttpRequest httpRequest) {
        String userId = SessionStorage.findSessionBy(httpRequest.getSessionId().get()).getUserId();
        Board board = new Board(userId, httpRequest.getBody().get("contents"));
        //DB 넣는 작업
        boardService.addBoard(board);

        HttpResponse httpResponse = new HttpResponse();
        httpResponse.sendRedirect("/index.html");
        return httpResponse;
    }
}
