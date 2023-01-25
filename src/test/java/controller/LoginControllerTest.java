package controller;

import db.SessionRepository;
import db.UserRepository;
import exception.FileNotFoundException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.SessionService;
import service.UserService;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LoginControllerTest {

    private final UserRepository userRepository = new UserRepository();
    private final SessionRepository sessionRepository = new SessionRepository();
    private final UserService userService = new UserService(userRepository);
    private final SessionService sessionService = new SessionService(sessionRepository);

    Controller controller = new UserLogInController(sessionService, userService);

    @Test
    @DisplayName("로그인 성공 테스트")
    void doPost() throws IOException, FileNotFoundException, URISyntaxException {
        String request =
                "POST /user/login HTTP/1.1" + System.lineSeparator() +
                        "Host: localhost:8080" + System.lineSeparator() +
                        "Connection: keep-alive" + System.lineSeparator() +
                        "Content-Length: 33" + System.lineSeparator() +
                        "Content-Type: application/x-www-form-urlencoded" + System.lineSeparator() +
                        "Accept: */*" + System.lineSeparator() +
                        System.lineSeparator() +
                        "userId=javajigi&password=password";

        InputStream in = new ByteArrayInputStream(request.getBytes());
        HttpRequest httpRequest = HttpRequest.from(in);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse httpResponse = HttpResponse.createDefaultHttpResponse(byteArrayOutputStream);

        User user = new User("javajigi", "password", "박재성", "javajigi@slipp.net");
        userRepository.addUser(user);

        controller.service(httpRequest, httpResponse);

        String response = byteArrayOutputStream.toString(StandardCharsets.UTF_8);

        assertAll(
                () -> assertThat(response).contains("HTTP/1.1 302 Found"),
                () -> assertThat(response).contains("Location: /index.html"),
                () -> assertThat(response).contains("Set-Cookie: JSESSIONID=")
        );


    }

    @Test
    @DisplayName("잘못된 ID 입력 시 로그인 실패 테스트")
    void loginFailedWithWrongId() throws IOException, FileNotFoundException, URISyntaxException {
        String request =
                "POST /user/login HTTP/1.1" + System.lineSeparator() +
                        "Host: localhost:8080" + System.lineSeparator() +
                        "Connection: keep-alive" + System.lineSeparator() +
                        "Content-Length: 28" + System.lineSeparator() +
                        "Content-Type: application/x-www-form-urlencoded" + System.lineSeparator() +
                        "Accept: */*" + System.lineSeparator() +
                        System.lineSeparator() +
                        "userId=aaa&password=password";

        InputStream in = new ByteArrayInputStream(request.getBytes());
        HttpRequest httpRequest = HttpRequest.from(in);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse httpResponse = HttpResponse.createDefaultHttpResponse(byteArrayOutputStream);

        User user = new User("javajigi", "password", "박재성", "javajigi@slipp.net");
        userRepository.addUser(user);

        controller.service(httpRequest, httpResponse);

        String response = byteArrayOutputStream.toString(StandardCharsets.UTF_8);

        assertAll(
                () -> assertThat(response).contains("HTTP/1.1 302 Found"),
                () -> assertThat(response).contains("Location: /user/login_failed.html")
        );

    }

    @Test
    @DisplayName("잘못된 PASSWORD 입력 시 로그인 실패 테스트")
    void loginFailedWithWrongPASSWORD() throws IOException, FileNotFoundException, URISyntaxException {
        String request =
                "POST /user/login HTTP/1.1" + System.lineSeparator() +
                        "Host: localhost:8080" + System.lineSeparator() +
                        "Connection: keep-alive" + System.lineSeparator() +
                        "Content-Length: 29" + System.lineSeparator() +
                        "Content-Type: application/x-www-form-urlencoded" + System.lineSeparator() +
                        "Accept: */*" + System.lineSeparator() +
                        System.lineSeparator() +
                        "userId=javajigi&password=pass";

        InputStream in = new ByteArrayInputStream(request.getBytes());
        HttpRequest httpRequest = HttpRequest.from(in);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse httpResponse = HttpResponse.createDefaultHttpResponse(byteArrayOutputStream);

        User user = new User("javajigi", "password", "박재성", "javajigi@slipp.net");
        userRepository.addUser(user);

        controller.service(httpRequest, httpResponse);

        String response = byteArrayOutputStream.toString(StandardCharsets.UTF_8);

        assertAll(
                () -> assertThat(response).contains("HTTP/1.1 302 Found"),
                () -> assertThat(response).contains("Location: /user/login_failed.html")
        );

    }


}
