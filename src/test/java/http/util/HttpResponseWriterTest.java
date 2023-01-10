package http.util;

import http.common.HttpBody;
import http.common.HttpStatus;
import http.response.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("ttpResponseWriter Test")
public class HttpResponseWriterTest {

    @Test
    @DisplayName("write() - index.html 페이지 조회 정상 응답 케이스")
    void test() {
        // given
        HttpResponse response = new HttpResponse();
        response.setStatus(HttpStatus.OK);
        HttpBody body = new HttpBody("Hello World".getBytes());
        response.setBody(body);
        response.addHeader("Content-Type", "text/plain");

        // when
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponseWriter.write(byteArrayOutputStream, response);

        // then
        assertEquals(
                "HTTP/1.1 200 OK\r\n" +
                        "Content-Length : 11\r\n" +
                        "Content-Type : text/plain\r\n" +
                        "\n" +
                        "Hello World",
                byteArrayOutputStream.toString()
        );
    }
}
