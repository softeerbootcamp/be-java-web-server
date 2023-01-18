package util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class HttpParserTest {

    @Test
    void 쿼리스트링_파싱테스트() {
        Map map = HttpParser.parseQueryString("create?userId=아이디&password=password&name=박원종&email=wonjong@naver.com");

        Map<String, String> expectedMap = new HashMap<>();
        expectedMap.put("userId", "아이디");
        expectedMap.put("password", "password");
        expectedMap.put("name", "박원종");
        expectedMap.put("email", "wonjong@naver.com");

        for (String key : expectedMap.keySet()) {
            assertThat(map.get(key)).isEqualTo(expectedMap.get(key));
        }
    }
    @Test
    void 세션ID_파싱_테스트() {
        String sid = HttpParser.parseSessionId("SID=e72040da-00a6-47d7-8ac9-78f17dbb7b4d");

        assertThat(sid).isEqualTo("e72040da-00a6-47d7-8ac9-78f17dbb7b4d");
    }
}