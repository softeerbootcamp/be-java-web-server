package controller;

import db.SessionStorage;
import db.UserDAO;
import model.User;
import model.request.Request;
import model.request.UserCreate;
import model.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.AuthInterceptor;
import webserver.controller.UserLogoutController;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;

import static model.response.HttpStatusCode.FOUND;
import static org.assertj.core.api.Assertions.assertThat;

public class UserLogoutControllerTest {

    UserDAO userDAO = new UserDAO();

    @BeforeEach
    void cleanDb() throws SQLException {
        userDAO.deleteAll();
        SessionStorage.cleanAll();
    }

    @Test
    @DisplayName("유저 로그아웃 테스트")
    void userLogout() throws Exception {
        //given
        UserCreate user = new UserCreate("test", "123", "tester", "test@test.com");
        userDAO.insert(user);
        String sid = "123456";
        SessionStorage.addSession(sid, userDAO.findByUserId("test"));

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
