package http.util;

import http.common.URL;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("HttpURIParser Test")
public class HttpURLParserTest {

    @Test
    @DisplayName("parse() - 쿼리스트링이 존재하지 않는 경우")
    void testParse() {
        // given
        String requestLine = "GET /index.html HTTP/1.1";

        // when
        URL parse = HttpURIParser.parse(requestLine);

        // then
        assertAll(
                () -> assertEquals("/index.html", parse.getPath()),
                () -> assertEquals(0, parse.getQueries().size())
        );
    }

    @Test
    @DisplayName("parse() - 쿼리스트링이 존재하는 경우")
    void testParseWhenExistQueryParam() {
        // given
        String requestLine = "GET /index.html?name=sol&age=26 HTTP/1.1";

        // when
        URL parse = HttpURIParser.parse(requestLine);

        // then
        assertAll(
                () -> assertEquals("/index.html", parse.getPath()),
                () -> assertEquals(2, parse.getQueries().size()),
                () -> assertEquals("sol", parse.getQueries().get("name")),
                () -> assertEquals("26", parse.getQueries().get("age"))
        );
    }

    @Test
    @DisplayName("parse() - 쿼리스트링이 /index? 와 같은 케이스")
    void testParseInvalidCase1() {
        // given
        String requestLine = "GET /index? HTTP/1.1";

        // when
        URL parse = HttpURIParser.parse(requestLine);

        // then
        assertAll(
                () -> assertEquals("/index", parse.getPath()),
                () -> assertEquals(0, parse.getQueries().size())
        );
    }

    @Test
    @DisplayName("parse() - 쿼리스트링이 /index?name= 와 같은 케이스")
    void testParseInvalidCase2() {
        // given
        String requestLine = "GET /index?name= HTTP/1.1";

        // when
        URL parse = HttpURIParser.parse(requestLine);

        // then
        assertAll(
                () -> assertEquals("/index", parse.getPath()),
                () -> assertEquals(1, parse.getQueries().size()),
                () -> assertEquals("", parse.getQueries().get("name"))
        );
    }

    @Test
    @DisplayName("parse() - 쿼리스트링이 /index?name=&age= 와 같은 케이스")
    void testParseInvalidCase3() {
        // given
        String requestLine = "GET /index?name= HTTP/1.1";

        // when
        URL parse = HttpURIParser.parse(requestLine);

        // then
        assertAll(
                () -> assertEquals("/index", parse.getPath()),
                () -> assertEquals(1, parse.getQueries().size()),
                () -> assertEquals("", parse.getQueries().get("name"))
        );
    }

    @Test
    @DisplayName("parse() - 쿼리스트링이 /index?name&age 와 같은 케이스")
    void testParseInvalidCase4() {
        // given
        String requestLine = "GET /index?name&age HTTP/1.1";

        // when
        URL parse = HttpURIParser.parse(requestLine);

        // then
        assertAll(
                () -> assertEquals("/index", parse.getPath()),
                () -> assertEquals(2, parse.getQueries().size()),
                () -> assertEquals("", parse.getQueries().get("name")),
                () -> assertEquals("", parse.getQueries().get("age"))
        );
    }
}
