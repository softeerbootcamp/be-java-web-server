package was.controller;

import db.BoardDatabase;
import model.Board;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.domain.HttpRequest;
import webserver.domain.HttpRequestMessage;
import webserver.domain.HttpResponseMessage;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BoardControllerTest {
    @Test
    @DisplayName("board api 테스트")
    void board() {
        Map<String, String> header = new HashMap<>();
        header.put("Request Line", "GET /index.html HTTP/1.1");
        Map<String, String> body = new HashMap<>();
        body.put("writer", "박원종");
        body.put("title", "제목");
        body.put("content", "내용");
        HttpRequestMessage httpRequestMessage = new HttpRequestMessage(header, body);

        BoardController boardController = BoardController.getInstance();
        HttpResponseMessage httpResponseMessage = boardController.insertBoards(HttpRequest.newInstance(httpRequestMessage));

        //내가 원하는 board가 db에 들어가 있는지
        Board find = BoardDatabase.findBoardByTitle("제목");
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(find.getWriter()).isEqualTo("박원종");
        softly.assertThat(find.getTitle()).isEqualTo("제목");
        softly.assertThat(find.getContent()).isEqualTo("내용");
        softly.assertAll();
    }

}