package request;

import model.request.RequestLine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestLineTest {
    @Test
    @DisplayName("RequestLine 생성 Test")
    void RequestLine_생성_Test() {
        String line = "GET /index.html HTTP/1.1";

        RequestLine requestLine = RequestLine.from(line);

        assertThat(requestLine).isInstanceOf(RequestLine.class);
    }

    @Test
    @DisplayName("RequestLine에서 Controller 매핑하기 위한 데이터 파싱 Test")
    void getControllerCriteria() {
        String line = "GET /user/create?" +
                "userId=javajigi&password=password" +
                "&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1";

        RequestLine requestLine = RequestLine.from(line);
        String criteria = requestLine.getControllerCriteria();

        assertThat(criteria).isEqualTo("user");
    }
}
