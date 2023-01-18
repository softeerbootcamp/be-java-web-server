package request;

import model.Session;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.Cookie;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class RequestHeaderTest {
    public static final byte[] INCLUDE_COOKIE_REQUEST = ("GET /index.html HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Accept: */*\n"+
            "Cookie: sid=123456").getBytes();

    @Test
    @DisplayName("requestHeader를 통한 Cookie 필드값 설정 테스트")
    void readHeaderContent() throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(INCLUDE_COOKIE_REQUEST);
        //when
        RequestHeader requestHeader = HttpRequest.getHttpRequest(inputStream).getRequestHeader();
        //then
        assertAll(
                () -> assertEquals(requestHeader.getCookie().getKey(), Session.SESSION_ID),
                () -> assertEquals(requestHeader.getCookie().getValue(), "123456")
        );
    }

}