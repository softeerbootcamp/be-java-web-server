package service;

import db.BoardDatabase;
import db.JdbcTemplate;
import model.Board;

import java.util.List;

public class BoardService {
    private static BoardService boardService;
    private static JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();

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
        jdbcTemplate.insertIntoBoardDb(board);
    }
    public List<Board> getAllBoards() {
        return jdbcTemplate.findAllBoards();
    }
}
