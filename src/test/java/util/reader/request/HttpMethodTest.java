package util.reader.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import request.HttpMethod;

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
    @DisplayName("메소드명에 따른 POST httpMethod enum 가져오기 테스트")
    void getPOSTMethod() {
        String post = "POST";
        HttpMethod method = HttpMethod.getMethod(post);
        assertThat(method).isEqualTo(HttpMethod.POST);
    }
}