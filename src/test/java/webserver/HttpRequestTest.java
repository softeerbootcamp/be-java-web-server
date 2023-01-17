package webserver;

import org.junit.jupiter.api.Test;
import webserver.domain.HttpRequest;
import webserver.domain.HttpRequestMessage;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {
    @Test
    void HTTP_요청라인_분리_테스트() {
        Map<String, String> map = new HashMap<>();
        map.put("Request Line", "GET /index.html HTTP/1.1");
        map.put("HOST", "www.test01.com");
        HttpRequest httpRequest = HttpRequest.newInstance(new HttpRequestMessage(map, null));

        assertThat(httpRequest.getRequestLine().getHttpMethod().getMethod()).isEqualTo("GET");
        assertThat(httpRequest.getRequestLine().getUrl()).isEqualTo("/index.html");
        assertThat(httpRequest.getRequestLine().getHttpVersion()).isEqualTo("HTTP/1.1");
        Map<String, String> resultMap = httpRequest.getHeaders();
        assertThat(resultMap.size()).isEqualTo(1);
        assertThat(resultMap.get("HOST")).isEqualTo("www.test01.com");
    }
}