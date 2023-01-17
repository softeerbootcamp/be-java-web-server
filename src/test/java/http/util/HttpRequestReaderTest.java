package http.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("HttpRequestReader Test")
public class HttpRequestReaderTest {

    private final String request =
            "POST /user/create HTTP/1.1\n" +
                    "Host: localhost:8080\n" +
                    "Connection: keep-alive\n" +
                    "Content-Length: 59\n" +
                    "Content-Type: application/x-www-form-urlencoded\n" +
                    "Accept: */*\n" +
                    "\n" +
                    "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

    @Test
    void readRequestLine() throws IOException {
        // given
        InputStream in = new ByteArrayInputStream(request.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        // when
        String requestLine = HttpRequestReader.readRequestLine(br);

        // then
        assertEquals("POST /user/create HTTP/1.1", requestLine);
    }

    @Test
    void readHeader() throws IOException {
        // given
        String headers = "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 59\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n";
        InputStream in = new ByteArrayInputStream(request.substring(27).getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        // when
        String readHeader = HttpRequestReader.readHeader(br);

        // then
        assertEquals(headers, readHeader);
    }

    @Test
    void readBody() throws IOException {
        // given
        String body = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        InputStream in = new ByteArrayInputStream(request.substring(151).getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        // when
        String readBody = HttpRequestReader.readBody(br, 93);
//
//        // then
        assertEquals(body, readBody);
    }
}
