package request;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class HttpRequestTest {
    private final byte[] BASIC_REQUEST = ("GET /index.html HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*").getBytes();

    private final byte[] BASIC_REQUEST_INCLUDE_BODY = ("POST /user/create HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Content-Length: 59\n" +
            "Content-Type: application/x-www-form-urlencoded\n" +
            "Accept: */*\n" +
            "\n" +
            "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net").getBytes();

    @Test
    @DisplayName("RequestLine 읽기 Test")
    void getHttpRequestLine() throws IOException {
        //given
        ByteArrayInputStream inputStream = new ByteArrayInputStream(BASIC_REQUEST);
        //when
        HashMap<String, String> headerContents = HttpRequest.getHttpRequest(inputStream).getRequestHeader().getHeaderContents();
        //then
        assertThat(headerContents.get(RequestHeader.REQUEST_LINE)).isEqualTo("GET /index.html HTTP/1.1");
    }

    @Test
    @DisplayName("Request의 Host,Connection, Accept읽기 테스트")
    void getHttpRequestAllContent() throws IOException {
        //given
        ByteArrayInputStream inputStream = new ByteArrayInputStream(BASIC_REQUEST);
        //when
        HashMap<String, String> headerContents = HttpRequest.getHttpRequest(inputStream).getRequestHeader().getHeaderContents();
        //then
        assertThat(headerContents.get("Host")).isEqualTo("localhost:8080");
        assertThat(headerContents.get("Connection")).isEqualTo("keep-alive");
        assertThat(headerContents.get("Accept")).isEqualTo("*/*");
    }


    @Test
    @DisplayName("Request의 Body읽기 테스트")
    void getHttpRequestBody() throws IOException {
        //given
        ByteArrayInputStream inputStream = new ByteArrayInputStream(BASIC_REQUEST_INCLUDE_BODY);
        //when
        StringBuilder data = HttpRequest.getHttpRequest(inputStream).getRequestBody().getData();
        //then
        assertThat(data.toString()).isEqualTo("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
    }
}