package controller;

import db.SessionRepository;
import db.UserRepository;
import exception.FileNotFoundException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.Session;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.SessionService;
import service.UserService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserListControllerTest {

    private final UserRepository userRepository = new UserRepository();
    private final SessionRepository sessionRepository = new SessionRepository();
    private final UserService userService = new UserService(userRepository);
    private final SessionService sessionService = new SessionService(sessionRepository);

    Controller controller = new UserListController(sessionService, userService);

    @Test
    @DisplayName("로그인 상태에서 유저리스트 요청 시 유저리스트 화면으로 이동하는지 테스트")
    void doGetWithLogin() throws IOException, FileNotFoundException, URISyntaxException, SQLException {
        String request = "GET /user/login HTTP/1.1" + System.lineSeparator() +
                "Host: localhost:8080" + System.lineSeparator() +
                "Cookie: JSESSIONID=123;" + System.lineSeparator();

        InputStream in = new ByteArrayInputStream(request.getBytes());
        HttpRequest httpRequest = HttpRequest.from(in);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse httpResponse = HttpResponse.createDefaultHttpResponse(byteArrayOutputStream);

        User user = User.of("javajigi", "password", "박재성", "javajigi@slipp.net");
        try {
            userRepository.addUser(user);
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new RuntimeException(e);
        }

        Session session = Session.of("123","javajigi","박재성");
        sessionRepository.addSession("123", session);
        controller.service(httpRequest, httpResponse);

        String response = byteArrayOutputStream.toString();

        assertAll(
                () -> assertThat(response).contains("HTTP/1.1 200 OK"),
                () -> assertThat(response).contains("Content-Type: text/html;charset=utf-8")
        );
        userRepository.deleteById("javajigi");
    }

    @Test
    @DisplayName("비 로그인 상태에서 유저리스트 요청 시 로그인 폼으로 이동하는지 테스트")
    void doGetWithNotLogin() throws IOException, FileNotFoundException, URISyntaxException, SQLException {
        String request = "GET /user/login HTTP/1.1" + System.lineSeparator() +
                "Host: localhost:8080" + System.lineSeparator() +
                "Cookie: JSESSIONID=123;" + System.lineSeparator();

        InputStream in = new ByteArrayInputStream(request.getBytes());
        HttpRequest httpRequest = HttpRequest.from(in);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse httpResponse = HttpResponse.createDefaultHttpResponse(byteArrayOutputStream);

        User user = User.of("javajigi", "password", "박재성", "javajigi@slipp.net");
        try {
            userRepository.addUser(user);
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new RuntimeException(e);
        }

        controller.service(httpRequest, httpResponse);

        String response = byteArrayOutputStream.toString();

        assertAll(
                () -> assertThat(response).contains("HTTP/1.1 302 Found"),
                () -> assertThat(response).contains("Location: /user/login")
        );
        userRepository.deleteById("javajigi");
    }
}
