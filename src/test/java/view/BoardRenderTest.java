package view;

import model.Board;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reader.fileReader.FileReader;
import reader.fileReader.TemplatesFileReader;
import request.RequestDataType;
import request.Url;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BoardRenderTest {

    BoardRender boardRender = BoardRender.getInstance();
    FileReader fileReader = new TemplatesFileReader();

    @Test
    @DisplayName("board 상세보기 페이지 렌더링 테스트")
    void addBoardInfo() throws IOException {
        //given
        Board board = new Board(1L, "test", "hi", "content", Timestamp.valueOf(LocalDateTime.now()));
        byte[] indexData = fileReader.readFile(new Url("/qna/show.html", RequestDataType.FILE));
        //when
        byte[] fixedData = boardRender.addBoardInfo(indexData, board);
        //then
        Assertions.assertAll(
                () -> assertTrue(new String(fixedData).contains(String.valueOf(board.getId()))),
                () -> assertTrue(new String(fixedData).contains(String.valueOf(board.getTitle()))),
                () -> assertTrue(new String(fixedData).contains(String.valueOf(board.getContent()))
                ));
    }
}