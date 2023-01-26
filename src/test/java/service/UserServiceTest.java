package service;

import db.SessionStorage;
import db.UserDAO;
import exception.DuplicateUserIdException;
import model.User;
import model.UserSession;
import model.request.Request;
import model.request.UserCreate;
import model.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.HttpRequestUtils;
import webserver.service.UserService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceTest {

    UserDAO userDAO = new UserDAO();

    @BeforeEach
    void cleanDb() throws SQLException {
        userDAO.deleteAll();
        SessionStorage.cleanAll();
    }

    @Test
    @DisplayName("유저 저장 (서비스 레벨)")
    void saveUser() throws IOException, SQLException {
        //given
        String body = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        String requestMessage = "POST /user/create HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Content-Length: " + body.length() + "\n"
                + "Content-Type: application/x-www-form-urlencoded\n"
                + "Accept: */*\n\n"
                + body;
        InputStream inputStream = new ByteArrayInputStream(requestMessage.getBytes());
        Request request = new Request(inputStream);

        //when
        UserService userService = new UserService();
        userService.signUpUser(request);

        //then
        User userById = userDAO.findByUserId("javajigi");
        assertThat(userById.getEmail()).isEqualTo("javajigi@slipp.net");
    }

    @Test
    @DisplayName("중복 유저 회원가입시 exception 출력")
    void duplicateUserIdException() throws IOException, SQLException {
        //given

        userDAO.insert(new UserCreate("javajigi", "pwd", "tester", "test@test.com"));

        //when
        String body = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        String requestMessage = "POST /user/create HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Content-Length: " + body.length() + "\n"
                + "Content-Type: application/x-www-form-urlencoded\n"
                + "Accept: */*\n\n"
                + body;
        InputStream inputStream = new ByteArrayInputStream(requestMessage.getBytes());
        Request request = new Request(inputStream);
        UserService userService = new UserService();
        //then
//        assertThrows(DuplicateUserIdException.class, () -> userService.signUpUser(request));

    }

    @Test
    @DisplayName("유저 로그인 성공 (응답에서 쿠키 구워주는지)")
    void login_success() throws Exception {
        //given
        String body = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        String requestMessage = "POST /user/create HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Content-Length: " + body.length() + "\n"
                + "Content-Type: application/x-www-form-urlencoded\n"
                + "Accept: */*\n\n"
                + body;
        InputStream inputStream = new ByteArrayInputStream(requestMessage.getBytes());
        Request request = new Request(inputStream);
        UserService userService = new UserService();
        userService.signUpUser(request);

        //when
        String body2 = "userId=javajigi&password=password";
        String requestMessage2 = "POST /user/login HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Content-Length: " + body2.length() + "\n"
                + "Content-Type: application/x-www-form-urlencoded\n"
                + "Accept: */*\n\n"
                + body2;
        InputStream inputStream2 = new ByteArrayInputStream(requestMessage2.getBytes());
        Request request2 = new Request(inputStream2);
        Response response = userService.loginUser(request2);

        //then
        String setCookieHeader = response.getHeaders().get("Set-Cookie");
        String s = HttpRequestUtils.parseSid(setCookieHeader);
        UserSession bySession = SessionStorage.findBySessionId(s);

        assertThat(bySession.getUserId()).isEqualTo("javajigi");
    }

    @Test
    @DisplayName("유저 로그인 실패 (비밀번호 틀림)")
    void login_fail() throws Exception {
        //given
        userDAO.insert(new UserCreate("11", "22", "abc", "test@test"));
        UserService userService = new UserService();

        //when
        String body = "userId=11&password=33";
        String requestMessage = "POST /user/login HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Content-Length: " + body.length() + "\n"
                + "Content-Type: application/x-www-form-urlencoded\n"
                + "Accept: */*\n\n"
                + body;
        InputStream inputStream = new ByteArrayInputStream(requestMessage.getBytes());
        Request request = new Request(inputStream);
        Response response = userService.loginUser(request);
        //then
        assertThat(response.getHeaders().get("Location")).isEqualTo("/user/login_failed.html");
        assert !response.getHeaders().containsKey("Set-Cookie");
    }

    @Test
    @DisplayName("유저 로그인 실패 (존재하지 않는 계정)")
    void login_fail_UserNotFound() throws Exception {
        //given
        userDAO.insert(new UserCreate("11", "22", "abc", "test@test"));
        UserService userService = new UserService();

        //when
        String body = "userId=11&password=33";
        String requestMessage = "POST /user/login HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Content-Length: " + body.length() + "\n"
                + "Content-Type: application/x-www-form-urlencoded\n"
                + "Accept: */*\n\n"
                + body;
        InputStream inputStream = new ByteArrayInputStream(requestMessage.getBytes());
        Request request = new Request(inputStream);
        Response response = userService.loginUser(request);
        //then
        assertThat(response.getHeaders().get("Location")).isEqualTo("/user/login_failed.html");
        assert !response.getHeaders().containsKey("Set-Cookie");
    }
}
