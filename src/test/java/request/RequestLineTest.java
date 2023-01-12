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
}
