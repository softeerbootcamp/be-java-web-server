package http.request;

import http.request.RequestHeader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

public class RequestHeaderTest {

    @DisplayName("http.request.RequestHeaderTest")
    @Test
    public void RequestHeaderTest() {
        List<String> requestHeaders = Arrays.asList("Host: localhost:8080", "Accept: text/plain", "Accept-Language: ko-KR");
        RequestHeader requestHeader = RequestHeader.from(requestHeaders);

        assertEquals("localhost:8080", requestHeader.getHeader("Host"));
        assertEquals("text/plain", requestHeader.getHeader("Accept"));
        assertEquals("ko-KR", requestHeader.getHeader("Accept-Language"));
        assertEquals("text/plain", requestHeader.getContentType());
    }

}
