package controller;

import model.Request.Request;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRequestHandlerTest {

    @Test
    @DisplayName("입력된 request line에서 url 추출하는 메서드")
    void extractUrl() throws IOException {
        //given
        String requestMessage = "GET /index.html HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Accept: */*\n";
        InputStream inputStream = new ByteArrayInputStream(requestMessage.getBytes());
        //when
        Request request = new Request(inputStream);
        String url = request.getUrl();

        //then
        assertThat(url).isEqualTo("/index.html");

    }

    @Test
    @DisplayName("입력된 request line이 없을 경우 extractUrl 메서드가 index를 반환하는지 여부 체크")
    void extractUrl_noUrl() throws IOException {
        //given
        String requestMessage = "GET / HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Accept: */*";
        InputStream inputStream = new ByteArrayInputStream(requestMessage.getBytes());

        //when
        Request request = new Request(inputStream);
        String url = request.getUrl();
        //then
        assertThat(url).isEqualTo("/index.html");

    }

}
