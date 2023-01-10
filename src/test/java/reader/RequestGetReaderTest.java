package reader;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import request.HttpRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RequestGetReaderTest {

    @Test
    @DisplayName("Get 메서드의 요청의 경우 url파싱 테스트")
    void findPathInRequest() {
        final String requestHeader = "GET /index.html HTTP/1.1";
        HttpRequest httpRequest = new HttpRequest(List.of(requestHeader));
        RequestReader requestGetReader = new RequestGetReader();
        Assertions.assertThat(requestGetReader.findPathInRequest(httpRequest)).isEqualTo("/index.html");
    }
}