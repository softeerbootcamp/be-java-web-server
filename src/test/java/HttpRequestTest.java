import http.Method;
import http.RequestHeader;
import http.RequestLine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {
    @DisplayName("쿼리문이 없는 요청을 받아 인스턴스로 저장")
    @Test
    void 쿼리문_존재X_Test() {
        //given
        List<String> request = new ArrayList<>();
        request.add("GET /index.html HTTP/1.1");
        request.add("Host: localhost:8080");
        request.add("Connection: keep-alive");
        request.add("Accept: */*");


        //when
        RequestLine requestLine = RequestLine.from(request.get(0));
        RequestHeader requestHeader = RequestHeader.from(request.subList(1, request.size()));

        //then
        assertThat(requestLine).isNotNull();
        assertThat(requestHeader).isNotNull();

    }
}
