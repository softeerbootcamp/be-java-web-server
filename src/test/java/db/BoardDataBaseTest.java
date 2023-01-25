package db;

import model.Board;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class BoardDataBaseTest {

    BoardDataBase boardDataBase = BoardDataBase.getInstance();

    @Test
    @DisplayName("Board 저장 query 테스트")
    void insert() throws SQLException {
        //given
        Board board = new Board("test", "title", "content");

        //when
        boardDataBase.insert(board);
        //then
    }

}