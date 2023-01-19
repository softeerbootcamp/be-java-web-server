package controller;

import db.Database;
import db.SessionStorage;
import model.User;
import model.UserSession;
import model.request.Request;
import model.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.HttpRequestUtils;
import webserver.controller.UserLoginController;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static model.response.HttpStatusCode.FOUND;
import static org.assertj.core.api.Assertions.assertThat;

public class UserLoginControllerTest {

    @BeforeEach
    void cleanDb() {
        Database.cleanAll();
        SessionStorage.cleanAll();
    }

    @Test
    @DisplayName("유저 로그인 성공 (세션 저장 확인)")
    void login_session() throws Exception {
        //given
        Database.addUser(new User("javajigi", "password", "tester", "test@test.com"));

        //when
        String body = "userId=javajigi&password=password";
        String requestMessage = "POST /user/login HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Content-Length: " + body.length() + "\n"
                + "Content-Type: application/x-www-form-urlencoded\n"
                + "Accept: */*\n\n"
                + body;
        InputStream inputStream = new ByteArrayInputStream(requestMessage.getBytes());
        Request request = new Request(inputStream);

        UserLoginController userLoginController = new UserLoginController();
        Response response = userLoginController.service(request);

        //expected
        assertThat(response.getStatusLine().getHttpStatusCode()).isEqualTo(FOUND);
        assertThat(response.getHeaders().get("Location")).isEqualTo("/index.html");

        String sid = HttpRequestUtils.parseSid(response.getHeaders().get("Set-Cookie"));
        assert SessionStorage.isValidate(sid);

        UserSession bySession = SessionStorage.findBySessionId(sid);
        assertThat(bySession.getUserId()).isEqualTo("javajigi");
    }

    @Test
    @DisplayName("유저 로그인 실패 (login_failed 리다이렉트 여부, set-cookie 안하는지)")
    void login_failed() throws Exception {
        //given
        Database.addUser(new User("javajigi", "password", "tester", "test@test.com"));

        //when
        String body = "userId=javajigi&password=wrong";
        String requestMessage = "POST /user/login HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Content-Length: " + body.length() + "\n"
                + "Content-Type: application/x-www-form-urlencoded\n"
                + "Accept: */*\n\n"
                + body;
        InputStream inputStream = new ByteArrayInputStream(requestMessage.getBytes());
        Request request = new Request(inputStream);

        UserLoginController userLoginController = new UserLoginController();
        Response response = userLoginController.service(request);

        //expected
        assertThat(response.getStatusLine().getHttpStatusCode()).isEqualTo(FOUND);
        assertThat(response.getHeaders().get("Location")).isEqualTo("/user/login_failed.html");

        assertThat(response.getHeaders().get("Set-Cookie")).isNull();
    }
}
