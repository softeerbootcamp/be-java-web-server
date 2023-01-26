package view;

import db.BoardDataBase;
import model.Board;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reader.fileReader.FileReader;
import reader.fileReader.TemplatesFileReader;
import request.RequestDataType;
import request.Url;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class HomeRenderTest {

    FileReader fileReader = new TemplatesFileReader();
    HomeRender homeRender = HomeRender.getInstance();
    BoardDataBase boardDataBase = BoardDataBase.getInstance();


    @Test
    @DisplayName("로그인 회원가입 태그 추가 테스트")
    void addSignInAndUpTag() throws IOException {
        //given
        byte[] index = fileReader.readFile(new Url("/index.html", RequestDataType.FILE));
        //when
        byte[] fixedHtmlData = homeRender.addSignInAndUpTag(index);
        //then
        assertThat(new String(fixedHtmlData)).isNotEqualTo(index);
    }

    @Test
    @DisplayName("홈 화면에서 사용자 이름 추가")
    void addUserName() throws IOException {
        //given
        String userName = "test";
        byte[] indexData = fileReader.readFile(new Url("/index.html", RequestDataType.FILE));
        //when
        byte[] fixedData = homeRender.addUserName(indexData, userName);
        //then
        assertThat(new String(fixedData).contains(userName)).isTrue();
    }

    @Test
    @DisplayName("홈 화면 Board 리스트 나열 확인")
    void addBoardList() throws IOException {
        //given
        byte[] indexData = fileReader.readFile(new Url("/index.html", RequestDataType.FILE));
        //when
        List<Board> boardList = boardDataBase.findAll();
        byte[] fixedData = homeRender.addBoardList(indexData, boardList);
        //then
        for (Board board : boardList) {
            assertThat(new String(fixedData).contains(board.getWriter())).isTrue();
            assertThat(new String(fixedData).contains(board.getTitle())).isTrue();
        }
    }
}