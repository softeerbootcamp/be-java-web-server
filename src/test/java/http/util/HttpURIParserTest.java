package http.util;

import http.common.URI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("HttpURIParser Test")
public class HttpURIParserTest {

    @Test
    @DisplayName("parse() - 쿼리스트링이 존재하지 않는 경우")
    void testParse() {
        // given
        String requestLine = "GET /index.html HTTP/1.1";

        // when
        URI parse = HttpURIParser.parse(requestLine);

        // then
        assertAll(
                () -> assertEquals("/index.html", parse.getPath()),
                () -> assertEquals(0, parse.getQuerys().size())
        );
    }

    @Test
    @DisplayName("parse() - 쿼리스트링이 존재하는 경우")
    void testParseWhenExistQueryParam() {
        // given
        String requestLine = "GET /index.html?name=sol&age=26 HTTP/1.1";

        // when
        URI parse = HttpURIParser.parse(requestLine);

        // then
        assertAll(
                () -> assertEquals("/index.html", parse.getPath()),
                () -> assertEquals(2, parse.getQuerys().size()),
                () -> assertEquals("sol", parse.getQuerys().get("name")),
                () -> assertEquals("26", parse.getQuerys().get("age"))
        );
    }

    @Test
    @DisplayName("parse() - 쿼리스트링이 /index? 와 같은 케이스")
    void testParseInvalidCase1() {
        // given
        String requestLine = "GET /index? HTTP/1.1";

        // when
        URI parse = HttpURIParser.parse(requestLine);

        // then
        assertAll(
                () -> assertEquals("/index", parse.getPath()),
                () -> assertEquals(0, parse.getQuerys().size())
        );
    }

    @Test
    @DisplayName("parse() - 쿼리스트링이 /index?name= 와 같은 케이스")
    void testParseInvalidCase2() {
        // given
        String requestLine = "GET /index?name= HTTP/1.1";

        // when
        URI parse = HttpURIParser.parse(requestLine);

        // then
        assertAll(
                () -> assertEquals("/index", parse.getPath()),
                () -> assertEquals(1, parse.getQuerys().size()),
                () -> assertEquals("", parse.getQuerys().get("name"))
        );
    }

    @Test
    @DisplayName("parse() - 쿼리스트링이 /index?name=&age= 와 같은 케이스")
    void testParseInvalidCase3() {
        // given
        String requestLine = "GET /index?name= HTTP/1.1";

        // when
        URI parse = HttpURIParser.parse(requestLine);

        // then
        assertAll(
                () -> assertEquals("/index", parse.getPath()),
                () -> assertEquals(1, parse.getQuerys().size()),
                () -> assertEquals("", parse.getQuerys().get("name"))
        );
    }

    @Test
    @DisplayName("parse() - 쿼리스트링이 /index?name&age 와 같은 케이스")
    void testParseInvalidCase4() {
        // given
        String requestLine = "GET /index?name&age HTTP/1.1";

        // when
        URI parse = HttpURIParser.parse(requestLine);

        // then
        assertAll(
                () -> assertEquals("/index", parse.getPath()),
                () -> assertEquals(2, parse.getQuerys().size()),
                () -> assertEquals("", parse.getQuerys().get("name")),
                () -> assertEquals("", parse.getQuerys().get("age"))
        );
    }
}
