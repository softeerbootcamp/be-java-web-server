package kr.codesquad.util;

import http.HttpMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.HttpRequestUtils;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestUtilsTest {

    String requestLine = "GET /index.html HTTP/1.1";

    @Test
    @DisplayName("HTTP request line에서 올바른 HTTP Method를 반환하는지 검증한다.")
    void getHttpMethod() {
        HttpMethod result = HttpRequestUtils.getHttpMethod(requestLine);

        assertThat(result).isEqualTo(HttpMethod.GET);
    }

    @Test
    @DisplayName("HTTP request line에서 URL을 추출하여 반환하는지 검증한다.")
    void getUrl() {
        String result = HttpRequestUtils.getUrl(requestLine);

        assertThat(result).isEqualTo("/index.html");
    }

    @Test
    @DisplayName("HTTP request line에서 HTTP Version을 추출하여 반환하는지 검증한다.")
    void getHttpVersion() {
        String result = HttpRequestUtils.getHttpVersion(requestLine);

        assertThat(result).isEqualTo("HTTP/1.1");
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

        Map<String, String> result = HttpRequestUtils.parseQueryString(queryString);

        assertThat(result.get("userId")).isEqualTo("abc");
        assertThat(result.get("password")).isEqualTo("aaa@@@");
        assertThat(result.get("name")).isEqualTo("임수민");
        assertThat(result.get("email")).isEqualTo("a@a");
    }

}
