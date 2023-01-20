package request;

import http.HttpHeader;
import http.request.HttpRequest;
import http.request.HttpRequestBody;
import http.request.HttpRequestLine;
import http.request.URI;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.enums.HttpMethod;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {
    private static HttpRequest httpRequest;

    @BeforeAll
    static void setUp() {
        HttpRequestLine requestLine = HttpRequestLine.of(HttpMethod.GET, URI.create("/user/create?id=mino&password=1234"),
                "HTTP/1.1");
        HttpHeader httpHeader = HttpHeader.from(new HashMap<>());
        HttpRequestBody requestBody = HttpRequestBody.from("hello");
        httpRequest = HttpRequest.of(requestLine, httpHeader, requestBody);
    }


    @Test
    void test_HttpRequest() {
        assertThat(httpRequest.getVersion()).isEqualTo("HTTP/1.1");
        assertThat(httpRequest.getQueryParams().get("id")).isEqualTo("mino");
        assertThat(httpRequest.getUri().getPath()).isEqualTo("/user/create");
    }
}

