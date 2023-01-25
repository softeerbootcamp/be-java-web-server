package was.controller;

import enums.HttpMethod;
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
            synchronized (boardController) {
                return new BoardController();
            }
        }
        return boardController;
    }
    @Auth
    @RequestMapping(method = HttpMethod.POST, value = "/board")
    public HttpResponseMessage insertBoards(HttpRequest httpRequest) {
        HttpResponse httpResponse = new HttpResponse();
        HttpParser.parseToBoard(httpRequest.getBody());
        
        return null;
    }
}
