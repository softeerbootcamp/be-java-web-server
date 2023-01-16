package http.util;

import http.common.HttpHeaders;
import http.common.URI;
import http.request.HttpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("HttpRequestParser Test")
public class HttpRequestParserTest {

    @Test
    @DisplayName("parse() - 헤더만 존재하는 경우")
    void testParseWhenExistHeaders() {
        // given
        String defaultGetRequest = "GET /index.html HTTP/1.1\n" +
                "User-Agent: Mozilla/4.0 ***\n" +
                "Host: localhost:8080\n" +
                "Accept-Language: en-us\n" +
                "Accept-Encoding: gzip, deflate\n" +
                "Connection: Keep-Alive\n\r";

        InputStream in = new ByteArrayInputStream(defaultGetRequest.getBytes(StandardCharsets.UTF_8));

        // when
        HttpRequest httpRequest = HttpRequestParser.parse(in);
        URI uri = httpRequest.getUri();
        HttpHeaders headers = httpRequest.getHeaders();

        // then
        assertAll(
                () -> assertEquals("/index.html", uri.getPath()),
                () -> assertEquals(0, uri.getQueries().size()),
                () -> assertEquals(5, headers.size()),
                () -> assertEquals("localhost:8080", headers.getValue("Host")));
    }

    @Test
    @DisplayName("parse() - 쿼리파람과 헤더가 존재하는 경우")
    void testParseWhenExistQueryParamAndHeaders() {
        // given
        String getRequestWithQueryParams =
                "GET /index.html?name=sol&age=26 HTTP/1.1\n" +
                "User-Agent: Mozilla/4.0 ***\n" +
                "Host: localhost:8080\n" +
                "Accept-Language: en-us\n" +
                "Accept-Encoding: gzip, deflate\n" +
                "Connection: Keep-Alive\n\r";
        InputStream in = new ByteArrayInputStream(getRequestWithQueryParams.getBytes(StandardCharsets.UTF_8));

        // when
        HttpRequest httpRequest = HttpRequestParser.parse(in);
        URI uri = httpRequest.getUri();
        HttpHeaders headers = httpRequest.getHeaders();

        // then
        assertAll(
                () -> assertEquals("/index.html", uri.getPath()),
                () -> assertEquals(2, uri.getQueries().size()),
                () -> assertEquals("sol", uri.getQueries().get("name")),
                () -> assertEquals("26", uri.getQueries().get("age")),
                () -> assertEquals(5, headers.size()),
                () -> assertEquals("localhost:8080", headers.getValue("Host")));
    }

    @Test
    @DisplayName("parse() - data(body)가 존재하는 경우")
    void test() {
        // given
        String getRequestWithData =
                "POST /user/create HTTP/1.1\n" +
                        "Host: localhost:8080\n" +
                        "Connection: keep-alive\n" +
                        "Content-Length: 93\n" +
                        "Content-Type: application/x-www-form-urlencoded\n" +
                        "Accept: */*\n" +
                        "\n" +
                        "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        InputStream in = new ByteArrayInputStream(getRequestWithData.getBytes(StandardCharsets.UTF_8));

        // when
        HttpRequest httpRequest = HttpRequestParser.parse(in);
        Map<String, String> data = httpRequest.getDatas();

        assertAll(
                () -> assertEquals("password", data.get("password")),
                () -> assertEquals("박재성", data.get("name")),
                () -> assertEquals("javajigi@slipp.net", data.get("email")),
                () -> assertEquals("javajigi", data.get("userId"))
        );
    }
}
