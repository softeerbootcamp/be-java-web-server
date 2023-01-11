package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpRequestUtilTest {

    @Test
    @DisplayName("파싱 정상 작동 테스트")
    void parseCorrectTest() {
        String queryString = "userId=kgstiger&password=123&name=김강산&email=kgstiger%40naver.com";
        Map<String, String> expected = HttpRequestUtil.parseQuerystring(queryString);
        assertEquals(expected.get("userId"), "kgstiger");
        assertEquals(expected.get("password"), "123");
        assertEquals(expected.get("name"), "김강산");
        assertEquals(expected.get("email"), "kgstiger@naver.com");
    }
}
