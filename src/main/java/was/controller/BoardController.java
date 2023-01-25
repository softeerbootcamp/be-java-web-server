package was.controller;

import db.BoardDatabase;
import enums.HttpMethod;
import model.Board;
import util.HttpParser;
import was.annotation.Auth;
import was.annotation.RequestMapping;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.domain.HttpResponseMessage;

public class BoardController implements Controller {
    private static BoardController boardController;
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
    @RequestMapping(method = HttpMethod.POST, value = "/board")
    public HttpResponseMessage insertBoards(HttpRequest httpRequest) {
        HttpResponse httpResponse = new HttpResponse();
        Board board = HttpParser.parseToBoard(httpRequest.getBody());

        //DB 넣는 작업
        BoardDatabase.addBoard(board);
        return new HttpResponseMessage(httpResponse.sendRedirect("/index.html"), httpResponse.getBody());
    }
}
