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
        String indexString = new String(fileReader.readFile(new Url("/index.html", RequestDataType.TEMPLATES_FILE)));
        //when
        byte[] fixedHtmlData = homeRender.addSignInAndUpTag(indexString);
        //then
        assertThat(new String(fixedHtmlData)).isNotEqualTo(indexString);
    }
}