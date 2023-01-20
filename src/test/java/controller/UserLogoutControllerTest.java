package controller;

import db.Database;
import db.SessionStorage;
import model.User;
import model.request.Request;
import model.response.HttpStatusCode;
import model.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.AuthInterceptor;
import webserver.controller.UserLogoutController;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static model.response.HttpStatusCode.FOUND;
import static org.assertj.core.api.Assertions.*;

public class UserLogoutControllerTest {

    @Test
    @DisplayName("유저 로그아웃 테스트")
    void userLogout() throws Exception {
        //given
        User user = new User("test", "123", "tester", "test@test.com");
        Database.addUser(user);
        String sid = "123456";
        SessionStorage.addSession(sid, user);

        //when
        String requestMessage = "POST /user/logout HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Cookie: " + "sid=123456\n"
                + "Accept: */*\n\n";
        InputStream inputStream = new ByteArrayInputStream(requestMessage.getBytes());
        Request request = new Request(inputStream);

        UserLogoutController userLogoutController = new UserLogoutController();
        Response response = userLogoutController.service(request);

        //then
        assertThat(response.getStatusLine().getHttpStatusCode()).isEqualTo(FOUND);
        assertThat(response.getHeaders().get("Location")).isEqualTo("/index.html");

        assert !AuthInterceptor.isAuthUser(request);
    }
}
