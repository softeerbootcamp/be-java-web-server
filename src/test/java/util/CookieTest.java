package util;

import model.Session;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import request.HttpRequest;
import request.RequestHeader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CookieTest {
    public static final byte[] INCLUDE_COOKIE_REQUEST = ("GET /index.html HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Accept: */*\n"+
            "Cookie: sid=123456").getBytes();

    public static final byte[] NOT_INCLUDE_COOKIE_REQUEST = ("GET /index.html HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Accept: */*").getBytes();


    @Test
    @DisplayName("requestHeader를 통한 Cookie 필드값 설정 테스트")
    void extractCookie() throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(INCLUDE_COOKIE_REQUEST);
        //when
        RequestHeader requestHeader = HttpRequest.getHttpRequest(inputStream).getRequestHeader();
        Optional<Cookie> cookieOptional = Cookie.extractCookie(requestHeader);
        Cookie cookie = cookieOptional.get();
        //then
        assertAll(
                () -> assertEquals(cookie.getKey(), Session.SESSION_ID),
                () -> assertEquals(cookie.getValue(), "123456")
        );
    }

    @Test
    @DisplayName("쿠키값이 없는경우 Cookie extract 테스트")
    void extractNotIncludeCookie() throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(NOT_INCLUDE_COOKIE_REQUEST);
        //when
        RequestHeader requestHeader = HttpRequest.getHttpRequest(inputStream).getRequestHeader();
        Optional<Cookie> cookieOptional = Cookie.extractCookie(requestHeader);
        //then
        Assertions.assertThat(cookieOptional.isEmpty()).isTrue();
    }


}