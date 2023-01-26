package db;

import model.Board;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class BoardDataBaseTest {

    BoardDataBase boardDataBase = BoardDataBase.getInstance();

    @Test
    @DisplayName("Board 저장 query 테스트")
    void insert() {
        //given
        Board board = new Board("test", "title", "content");
        //when
        boardDataBase.insert(board);
        //then
    }

    @Test
    @DisplayName("Board 전체조회 query 테스트")
    void findAll() {
        //given
        Board board = new Board("test", "title", "content");
        Board board2 = new Board("test2", "title2", "content2");
        boardDataBase.insert(board);
        boardDataBase.insert(board2);
        //when
        List<Board> boardList = boardDataBase.findAll();
        //then
        assertThat(boardList.size()).isEqualTo(2);
    }
}