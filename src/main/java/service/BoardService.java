package service;

import db.BoardRepository;

import java.time.LocalDate;
import java.util.Map;

public class BoardService {
    private final BoardRepository boardRepository;
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public void addBoard(Map<String, String> parameters) {
        LocalDate date = LocalDate.now();
        boardRepository.addBoard(
                date.toString(),
                parameters.get("author"),
                parameters.get("content"));
    }

}
