package view;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reader.fileReader.FileReader;
import reader.fileReader.TemplatesFileReader;
import request.RequestDataType;
import request.Url;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class HomeRenderTest {

    FileReader fileReader = new TemplatesFileReader();
    HomeRender homeRender = HomeRender.getInstance();


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
}