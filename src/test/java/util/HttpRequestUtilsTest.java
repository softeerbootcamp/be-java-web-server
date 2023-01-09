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
        String queryString = "GET /?name=sol&age=26 HTTP/1.1";

        // when
        Map<String, String> querys = HttpRequestUtils.parseQueryString(queryString);

        // then
        assertAll(
                () -> assertEquals("sol", querys.get("name")),
                () -> assertEquals("26", querys.get("age"))
        );

    }
}
