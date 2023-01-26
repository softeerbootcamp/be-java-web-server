package model.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RequestLineTest {
    @Test
    @DisplayName("RequestLine 생성 Test")
    void from() {
        String line = "GET /index.html HTTP/1.1";

        RequestLine requestLine = RequestLine.from(line);

        assertThat(requestLine).isInstanceOf(RequestLine.class);
    }

    @Test
    @DisplayName("Controller 매핑 데이터를 얻기 위한 RequestLine 파싱 Test")
    void getControllerCriteria() {
        String line = "GET /user/create?" +
                "userId=park1234&password=abc123&name=jun&email=jun@slipp.net HTTP/1.1";

        RequestLine requestLine = RequestLine.from(line);
        String criteria = requestLine.getControllerCriteria();

        assertThat(criteria).isEqualTo("user");
    }

    @Test
    @DisplayName("Query String 파싱 Test")
    void parseQueryString() {
        String line = "GET /user/create?" +
                "userId=park1234&password=abc123&name=jun&email=jun@slipp.net HTTP/1.1";

        RequestLine requestLine = RequestLine.from(line);
        Map<String, String> queryString = requestLine.parseQueryString();

        assertThat(queryString.get("userId")).isEqualTo("park1234");
        assertThat(queryString.get("password")).isEqualTo("abc123");
        assertThat(queryString.get("name")).isEqualTo("jun");
        assertThat(queryString.get("email")).isEqualTo("jun@slipp.net");
    }
}
