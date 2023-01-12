package request;

import http.request.HttpRequestLine;
import http.request.URI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.HttpMethod;

import static org.assertj.core.api.Assertions.*;

public class HttpRequestLineTest {
    @Test
    @DisplayName("requestLine 수정")
    void test_HttpRequestLine_create() {
        //given
        HttpRequestLine requestLine = HttpRequestLine.of(HttpMethod.GET, URI.create("/user/create?id=mino&password=1234"), "HTTP/1.1");

        //when
        String version = requestLine.getVersion();
        URI uri = requestLine.getUri();

        //then
        assertThat(version).isEqualTo("HTTP/1.1");
        assertThat(uri.getPath()).isEqualTo("/user/create");
        assertThat(uri.getQuerys().get("id")).isEqualTo("mino");
        assertThat(uri.getQuerys().get("password")).isEqualTo("1234");
    }
}
