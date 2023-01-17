package kr.codesquad.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.HttpRequestUtils;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestUtilsTest {

    @Test
    @DisplayName("요청 헤더에서 이름/값을 추출하여 반환하는지 검증한다.")
    void parseRequestHeader() {
        List<String> headers = List.of("Host: localhost:8080", "Connection: keep-alive","Accept: text/html");

        Map<String, String> result = HttpRequestUtils.parseRequestHeader(headers);

        assertThat(result.get("Host")).isEqualTo("localhost:8080");
        assertThat(result.get("Connection")).isEqualTo("keep-alive");
        assertThat(result.get("Accept")).isEqualTo("text/html");
    }

    @Test
    @DisplayName("URL에서 쿼리 스트링을 추출하여 반환하는지 검증한다.")
    void getQueryString() {
        String queryString = "userId=abc&password=aaa%40%40%40&name=%EC%9E%84%EC%88%98%EB%AF%BC&email=a%40a";
        String url = "/user/create?" + queryString;

        String result = HttpRequestUtils.getQueryString(url);

        assertThat(result).isEqualTo(queryString);
    }

    @Test
    @DisplayName("쿼리 스트링에서 이름/값을 파싱하여 반환하는지 검증한다.")
    void parseQueryString() {
        String queryString = "userId=abc&password=aaa%40%40%40&name=%EC%9E%84%EC%88%98%EB%AF%BC&email=a%40a";

        Map<String, String> result = HttpRequestUtils.parseBodyMessage(queryString);

        assertThat(result.get("userId")).isEqualTo("abc");
        assertThat(result.get("password")).isEqualTo("aaa@@@");
        assertThat(result.get("name")).isEqualTo("임수민");
        assertThat(result.get("email")).isEqualTo("a@a");
    }

}
