package request;

import http.request.HttpRequestBody;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class HttpRequestBodyTest {
    @Test
    @DisplayName("RequestBody 생성")
    void test_RequestBody_create() {
        HttpRequestBody httpRequestBody = HttpRequestBody.from("sid=1234&hello=1234");
        assertThat(httpRequestBody.getParams()).isEqualTo(Map.of("sid", "1234", "hello", "1234"));
    }
}
