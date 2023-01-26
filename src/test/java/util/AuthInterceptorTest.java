package util;

import db.SessionStorage;
import db.UserDAO;
import model.User;
import model.request.Request;
import model.request.UserCreate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.UUID;

public class AuthInterceptorTest {

    UserDAO userDAO = new UserDAO();

    @BeforeEach
    void cleanDb() throws SQLException {
        userDAO.deleteAll();
        SessionStorage.cleanAll();
    }

    @Test
    @DisplayName("Auth Interceptor 기능 테스트")
    void authInterceptorTest() throws Exception {
        //given
        UserCreate user = new UserCreate("aa", "bb", "cc", "test@test");
        userDAO.insert(user);
        String sid = String.valueOf(UUID.randomUUID());
        SessionStorage.addSession(sid, userDAO.findByUserId("aa"));

        //when
        String requestMessage = "GET /user/list.html HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Accept: */*\n"
                + "Cookie: sid=" + sid;
        InputStream inputStream = new ByteArrayInputStream(requestMessage.getBytes());
        Request request = new Request(inputStream);

        //then
        assert AuthInterceptor.isAuthUser(request);
    }

    @Test
    @DisplayName("Auth Interceptor 실패 테스트 (이상한 쿠키값)")
    void authInterceptorTest_fail() throws Exception {
        //given
        String sid = String.valueOf(UUID.randomUUID());
        //when
        String requestMessage = "GET /user/list.html HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Accept: */*\n"
                + "Cookie: sid=" + sid;
        InputStream inputStream = new ByteArrayInputStream(requestMessage.getBytes());
        Request request = new Request(inputStream);

        //then
        assert !AuthInterceptor.isAuthUser(request);
    }

    @Test
    @DisplayName("Auth Interceptor 복수 쿠키 요청 테스트")
    void authInterceptor_multiCookie() throws Exception {
        //given
        UserCreate user = new UserCreate("aa", "bb", "cc", "test@test");
        userDAO.insert(user);
        String sid = String.valueOf(UUID.randomUUID());
        SessionStorage.addSession(sid, userDAO.findByUserId("aa"));

        //when
        String requestMessage = "GET /user/list.html HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Accept: */*\n"
                + "Cookie: trash=wrongCookie; sid=" + sid;
        InputStream inputStream = new ByteArrayInputStream(requestMessage.getBytes());
        Request request = new Request(inputStream);
        //then
        assert AuthInterceptor.isAuthUser(request);
    }
}
