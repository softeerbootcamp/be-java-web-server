package request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reader.RequestReader;
import request.HttpMethod;
import request.HttpRequest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class HttpMethodTest {

    @Test
    @DisplayName("메소드명에 따른 GET httpMethod enum 가져오기 테스트")
    void getGETMethod() {
        String get = "GET";
        HttpMethod method = HttpMethod.getMethod(get);
        assertThat(method).isEqualTo(HttpMethod.GET);
    }

    @Test
    @DisplayName("client요청(HttpRequest)로 HttpMethod 찾기")
    void findMethod() {
        final String requestHeaderFirstLine = "GET /index.html HTTP/1.1";
        List<String> requestHeaders = new ArrayList<>();
        requestHeaders.add(requestHeaderFirstLine);
        HttpRequest httpRequest = new HttpRequest(requestHeaders);
        assertThat(HttpMethod.findMethod(httpRequest)).isEqualTo(HttpMethod.GET);
    }
    @Test
    @DisplayName("메소드명에 따른 POST httpMethod enum 가져오기 테스트")
    void getPOSTMethod() {
        String post = "POST";
        HttpMethod method = HttpMethod.getMethod(post);
        assertThat(method).isEqualTo(HttpMethod.POST);
    }
}