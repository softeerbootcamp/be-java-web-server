package service;

import db.SessionDatabase;
import model.Session;
import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import request.HttpRequest;
import request.RequestHeader;
import util.Cookie;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class SessionServiceTest {
    final String[] userValue = {"test", "1234", "testName", "test@naver.com"};

    public static final byte[] INCLUDE_COOKIE_REQUEST = ("GET /index.html HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Accept: */*\n"+
            "Cookie: sid=123456").getBytes();

    public static final byte[] NOT_INCLUDE_COOKIE_REQUEST = ("GET /index.html HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Accept: */*").getBytes();
    private SessionService sessionService = new SessionService();
    private SessionDatabase sessionDatabase = new SessionDatabase();
    @Test
    @DisplayName("쿠키를 통한 유요한 세션인지 테스트")
    void isUserLoggedIn() {
        //given
        SessionDatabase sessionDatabase = new SessionDatabase();
        User user = new User(userValue[0], userValue[1], userValue[2], userValue[3]);
        Session session = new Session(user);
        sessionDatabase.addData(session);
        Cookie cookie = new Cookie(Session.SESSION_ID, session.getUuid());
        //when
        boolean result = sessionService.isUserLoggedIn(cookie);
        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("유효한 User객체를 받아 세션에 저장후 쿠키생성")
    void persistUser() {
        //given
        User user = new User(userValue[0], userValue[1], userValue[2], userValue[3]);
        //when
        Cookie cookie = sessionService.persistUser(user);
        //then
        Optional<Session> result = sessionDatabase.findObjectById(cookie.getValue());
        assertThat(result.isPresent()).isTrue();
    }


    @Test
    @DisplayName("쿠키가 없는경우 valid 테스트")
    void notHavingCookie() throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(NOT_INCLUDE_COOKIE_REQUEST);
        //when
        RequestHeader requestHeader = HttpRequest.getHttpRequest(inputStream).getRequestHeader();
        boolean result = sessionService.cookieValidInSession(requestHeader);
        //then
        Assertions.assertThat(result).isFalse();
    }


}