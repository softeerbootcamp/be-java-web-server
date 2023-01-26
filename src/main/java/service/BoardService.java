package service;

import db.BoardDatabase;
import model.Board;

public class BoardService {
    private static BoardService boardService;

    private BoardService() {}

    public static BoardService getInstance() {
        if (boardService == null) {
            synchronized (BoardService.class) {
                boardService = new BoardService();
            }
        }
        return boardService;
    }

    public void addBoard(Board board) {
        BoardDatabase.addBoard(board);
    }
}
