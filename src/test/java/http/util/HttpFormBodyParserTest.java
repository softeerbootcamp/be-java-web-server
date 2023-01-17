package http.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("HttpFormBodyParser Test")
public class HttpFormBodyParserTest {

    @Test
    @DisplayName("parse - 정상 바디 데이터 형식 케이스")
    void parse() {
        // given
        String stringBody = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net\n";

        // when
        Map<String, String> data = HttpFormBodyParser.parse(stringBody);

        // then
        assertAll(
                () -> assertEquals("javajigi", data.get("userId")),
                () -> assertEquals("password", data.get("password")),
                () -> assertEquals("박재성", data.get("name")),
                () -> assertEquals("javajigi@slipp.net", data.get("email"))
        );
    }

    @Test
    @DisplayName("parse - 중간에 필드의 값이 비어있는 케이스")
    void parseWhenInvalid() {
        // given
        String stringBody = "userId=javajigi&password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net\n";

        // when
        Map<String, String> data = HttpFormBodyParser.parse(stringBody);

        // then
        assertAll(
                () -> assertEquals("javajigi", data.get("userId")),
                () -> assertEquals("", data.get("password")),
                () -> assertEquals("박재성", data.get("name")),
                () -> assertEquals("javajigi@slipp.net", data.get("email"))
        );
    }
}
