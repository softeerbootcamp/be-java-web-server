package util;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HttpRequestUtilTest {

    @Test
    void parseQueryString() {
        String reqURLParams = "userId=asdf&password=ljh1929&name=jaehun&email=wogns1602%40naver.com";
        Map<String, String> user_info = Map.of(
                "userId", "asdf",
                "password", "ljh1929",
                "name", "jaehun",
                "email", "wogns1602%40naver.com"
        );
        assertThat(HttpRequestUtil.parseQueryString(reqURLParams)).isEqualTo(user_info);
    }
}