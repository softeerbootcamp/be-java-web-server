package http.util;

import http.common.HttpHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("HttpHeaderParser Test")
public class HttpHeaderParserTest {

    @Test
    @DisplayName("parse() - 헤더가 있는 경우")
    void parse() {
        // given
        String strOfHeaders = "Host: localhost:8080\n" +
                "Connection: keep-alive\n";
        // when
        HttpHeaders headers = HttpHeaderParser.parse(strOfHeaders);

        // then
        assertAll(
                () -> assertEquals("localhost:8080", headers.getValue("Host")),
                () -> assertEquals("keep-alive", headers.getValue("Connection")));
    }

    @Test
    @DisplayName("parse() - 헤더가 없는 경우")
    void parseWhenNothing() {
        // given
        String strOfHeaders = "";

        // when
        HttpHeaders headers = HttpHeaderParser.parse(strOfHeaders);

        // then
        assertEquals(0, headers.size());
    }
}
