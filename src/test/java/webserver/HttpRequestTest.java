package webserver;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HttpRequestTest {
    @Test
    void HTTP_요청라인_분리_테스트() {
        Map<String, String> map = new HashMap<>();
        map.put("Request Line", "GET /index.html HTTP/1.1");
        map.put("HOST", "www.test01.com");
        HttpRequest httpRequest = HttpRequest.newInstance(map);

        assertThat(httpRequest.getRequestLine().getHttpMethod().getMethod()).isEqualTo("GET");
        assertThat(httpRequest.getRequestLine().getUrl()).isEqualTo("/index.html");
        assertThat(httpRequest.getRequestLine().getHttpVersion()).isEqualTo("HTTP/1.1");
        Map<String, String> resultMap = httpRequest.getHeaders();
        assertThat(resultMap.size()).isEqualTo(1);
        assertThat(resultMap.get("HOST")).isEqualTo("www.test01.com");
    }
}