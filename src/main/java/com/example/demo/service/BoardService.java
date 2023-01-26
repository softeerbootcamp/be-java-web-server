package com.example.demo.service;

import com.example.demo.model.Board;
import com.example.demo.repository.BoardRepository;

public class BoardService {

    static final BoardRepository boardRepository = new BoardRepository();

    public static void save(Board board) {
        boardRepository.insert(board);
    }

    public static void printList() {

    }
}
