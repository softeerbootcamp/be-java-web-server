package webserver.controller;

import model.dto.BoardDto;
import webserver.Service.BoardService;
import webserver.annotation.ControllerInfo;
import webserver.domain.ContentType;
import webserver.domain.RequestMethod;
import webserver.domain.StatusCodes;
import webserver.domain.request.Request;
import webserver.domain.response.Response;
import webserver.view.ModelAndView;

public class BoardController implements Controller{

    private BoardController (){}

    public static BoardController getInstance(){
        return BoardController.LazyHolder.INSTANCE;
    }

    private static class LazyHolder{  //Singleton
        private static final BoardController INSTANCE = new BoardController();
    }

    public BoardService boardService = BoardService.getInstance();

    @ControllerInfo(path = "/board/create", methodName = "createBoard", method = RequestMethod.POST)
    public void addBoard(BoardDto boardDto, Response res, ModelAndView mv){
        boardService.addBoard(boardDto);
        res.addHeaderAndBody(StatusCodes.OK, ("<script>alert('질문 생성이 완료되었습니다.'); history.go(-1); </script>").getBytes(), ContentType.TEXT_HTML);
    }

    @Override
    public void chain(Request req, Response res, ModelAndView mv) {
        ControllerInterceptor.executeController(getInstance(), req, res, mv);
    }
}
