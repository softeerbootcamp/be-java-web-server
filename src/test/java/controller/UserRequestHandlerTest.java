package controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.RequestHandler;

import java.net.Socket;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRequestHandlerTest {

    @Test
    @DisplayName("입력된 request line에서 url 추출하는 메서드")
    void extractUrl() throws Exception {
        //given
        RequestHandler requestHandler = new RequestHandler(new Socket());
        String reqLine = "GET /index.html HTTP/1.1";

        //when
        String url = requestHandler.extractUrl(reqLine);

        //then
        assertThat(url).isEqualTo("/index.html");

    }

    @Test
    @DisplayName("입력된 request line이 없을 경우 extractUrl 메서드가 index를 반환하는지 여부 체크")
    void extractUrl_noUrl() throws Exception {
        //given
        RequestHandler requestHandler = new RequestHandler(new Socket());
        String reqLine = "GET / HTTP/1.1";

        //when
        String url = requestHandler.extractUrl(reqLine);
        //then
        assertThat(url).isEqualTo("/index.html");

    }
}
