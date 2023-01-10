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
        HttpParser httpParser = new HttpParser();
        Map map = httpParser.parseQueryString("create?userId=아이디&password=password&name=박원종&email=wonjong@naver.com");

        Map<String, String> expectedMap = new HashMap<>();
        expectedMap.put("userId", "아이디");
        expectedMap.put("password", "password");
        expectedMap.put("name", "박원종");
        expectedMap.put("email", "wonjong@naver.com");

        for (String key : expectedMap.keySet()) {
            assertThat(map.get(key)).isEqualTo(expectedMap.get(key));
        }
    }
}