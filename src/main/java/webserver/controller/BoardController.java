package webserver.controller;

import model.dto.BoardDto;
import model.dto.CommentDto;
import model.dto.ReadBoardDto;
import webserver.Service.BoardService;
import webserver.annotation.ControllerInfo;
import webserver.annotation.PathVariable;
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

    @ControllerInfo(path = "/board/{boardId}/createComment", methodName = "createComment", method = RequestMethod.POST)
    public void addComment(@PathVariable String boardId, String contents, Response res, ModelAndView mv){
        boardService.addComment(contents, Integer.parseInt(boardId));
         res.addHeaderAndBody(StatusCodes.OK, ("<script>alert('질문 생성이 완료되었습니다.'); history.go(-1); </script>").getBytes(), ContentType.TEXT_HTML);
    }

    @ControllerInfo(path = "/board/show/{boardId}", methodName = "readBoard", method = RequestMethod.GET)
    public void readBoard(String boardId, Response res, ModelAndView mv){
        ReadBoardDto board = boardService.readBoard(Integer.parseInt(boardId));
        mv.addViewModel("board", board);
        mv.setViewPath("/board/show.html");
        res.addHeaderAndBody(StatusCodes.OK, ("<script>alert('질문 생성이 완료되었습니다.'); history.go(-1); </script>").getBytes(), ContentType.TEXT_HTML);
    }

    @Override
    public void chain(Request req, Response res, ModelAndView mv) {
        ControllerInterceptor.executeController(getInstance(), req, res, mv);
    }
}
