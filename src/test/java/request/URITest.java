package request;

import http.request.URI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class URITest {
    @Test
    @DisplayName("URI 생성 및 path 테스트")
    void test_URI_PATH() {
        URI uri = URI.create("/user/create?id=mino&password=1234");
        assertThat(uri.getPath()).isEqualTo("/user/create");
    }

    @Test
    @DisplayName("URI 생성 및 query 테스트")
    void test_URI_QUERY() {
        URI uri = URI.create("/user/create?id=mino&password=1234");
        assertThat(uri.getQuery().get("id")).isEqualTo("mino");
        assertThat(uri.getQuery().get("password")).isEqualTo("1234");
    }
}
