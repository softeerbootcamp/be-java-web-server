package util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("HttpRequestUtils Test")
public class HttpRequestUtilsTest {

    @Test
    @DisplayName("쿼리 스트링 파싱 테스트")
    void testParseQueryString() {
        // given
        String requestLine = "GET /?name=sol&age=26 HTTP/1.1";

        // when
        Map<String, String> querys = HttpRequestUtils.parseQueryString(requestLine);

        // then
        assertAll(
                () -> assertEquals(2, querys.size()),
                () -> assertEquals("sol", querys.get("name")),
                () -> assertEquals("26", querys.get("age"))
        );
    }

    @Test
    @DisplayName("쿼리 스트링 파싱 테스트 - 쿼리 스트링이 없는 경우")
    void testParseQueryStringWhenIsNotExist() {
        // given
        String requestLine = "GET / HTTP/1.1";

        // when
        Map<String, String> querys = HttpRequestUtils.parseQueryString(requestLine);

        // then
        assertEquals(0, querys.size());
    }

    @Test
    @DisplayName("요청 Path 파싱 테스트 - 쿼리스트링이 없는 경우")
    void testRequestURL() {
        // given
        String requestLine = "GET /favicon.ico HTTP/1.1";

        // when
        String path = HttpRequestUtils.requestPath(requestLine);

        // then
        assertEquals("/favicon.ico", path);
    }

    @Test
    @DisplayName("요청 Path 파싱 테스트 - 쿼리스트링이 있는 경우")
    void testRequestURLWhenExistQueryString() {
        // given
        String requestLine = "GET /index.html?name=sol&age=26 HTTP/1.1";

        // when
        String path = HttpRequestUtils.requestPath(requestLine);

        // then
        assertEquals("/index.html", path);
    }
}
