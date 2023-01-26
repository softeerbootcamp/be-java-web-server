package service;

import db.BoardRepository;
import model.Board;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

public class BoardService {
    private final BoardRepository boardRepository;
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public void addBoard(Map<String, String> parameters) {
        LocalDateTime dateTime = LocalDateTime.now();
        boardRepository.addBoard(
                dateTime.toString(),
                parameters.get("author"),
                parameters.get("content"));
    }

    public Collection<Board> findAll() {
        return boardRepository.findAll();
    }

}
