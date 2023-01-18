package util;

import db.Database;
import db.SessionStorage;
import model.User;
import model.request.Request;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;

public class AuthInterceptorTest {

    @Test
    @DisplayName("Auth Interceptor 기능 테스트")
    void authInterceptorTest() throws Exception {
        //given
        User user = new User("aa", "bb", "cc", "test@test");
        Database.addUser(user);
        String sid = String.valueOf(UUID.randomUUID());
        SessionStorage.addSession(sid, user);

        //when
        String requestMessage = "GET /user/list.html HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Accept: 음*/*\n"
                + "Cookie: sid=" + sid;
        InputStream inputStream = new ByteArrayInputStream(requestMessage.getBytes());
        Request request = new Request(inputStream);


        //when

        //then
        assert AuthInterceptor.isAuthUser(request);

    }
}
