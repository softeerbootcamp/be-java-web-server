package webserver.Service;

import db.BoardDatabase;
import model.Board;
import model.dto.BoardDto;
import webserver.security.SecurityContext;

import java.time.LocalDateTime;
import java.util.List;

public class BoardService {

    private BoardService (){}

    public static BoardService getInstance(){
        return BoardService.LazyHolder.INSTANCE;
    }

    private static class LazyHolder{   //Singleton
        private static final BoardService INSTANCE = new BoardService();
    }

    public void addBoard(BoardDto dto) {
        Board board = dto.toEntity();
        board.addMetadata(BoardDatabase.boardId.get(), SecurityContext.getContext().getUserId(), SecurityContext.getContext().getName(), LocalDateTime.now());
        BoardDatabase.addBoard(board);
    }

    public List<Board> getBoardList() {   //TODO : 이 메소드의 존재 이유를 고민해보자
        return BoardDatabase.findAll();
    }

}
