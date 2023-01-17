package request;

import http.request.HttpRequestBody;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class HttpRequestBodyTest {
    @Test
    @DisplayName("RequestBody 생성")
    void test_RequestBody_create() {
        HttpRequestBody httpRequestBody = HttpRequestBody.from("hello");

        assertThat(httpRequestBody.toString()).contains("hello");
    }
}
