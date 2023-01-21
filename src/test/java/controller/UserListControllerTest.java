package controller;

import db.SessionRepository;
import db.UserRepository;
import exception.FileNotFoundException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.Session;
import model.User;
import org.junit.jupiter.api.Test;
import service.SessionService;
import service.UserService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserListControllerTest {

    private final UserRepository userRepository = new UserRepository();
    private final SessionRepository sessionRepository = new SessionRepository();
    private final UserService userService = new UserService(userRepository);
    private final SessionService sessionService = new SessionService(sessionRepository);

    Controller controller = new UserListController(sessionService, userService);

    @Test
    void doGetWithLogin() throws IOException, FileNotFoundException, URISyntaxException {
        String request = "GET /user/login HTTP/1.1" + System.lineSeparator() +
                "Host: localhost:8080" + System.lineSeparator() +
                "Cookie: JSESSIONID=123;" + System.lineSeparator();

        InputStream in = new ByteArrayInputStream(request.getBytes());
        HttpRequest httpRequest = HttpRequest.from(in);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse httpResponse = HttpResponse.createDefaultHttpResponse(byteArrayOutputStream);

        User user = new User("javajigi", "password", "박재성", "javajigi@slipp.net");
        userRepository.addUser(user);

        Session session = new Session("123", "javajigi", "박재성");
        sessionRepository.addSession("123", session);

        controller.service(httpRequest, httpResponse);

        String response = byteArrayOutputStream.toString();

        assertAll(
                () -> assertThat(response).contains("HTTP/1.1 200 OK"),
                () -> assertThat(response).contains("Content-Type: text/html;charset=utf-8")
        );
    }

    @Test
    void doGetWithNotLogin() throws IOException, FileNotFoundException, URISyntaxException {
        String request = "GET /user/login HTTP/1.1" + System.lineSeparator() +
                "Host: localhost:8080" + System.lineSeparator() +
                "Cookie: JSESSIONID=123;" + System.lineSeparator();

        InputStream in = new ByteArrayInputStream(request.getBytes());
        HttpRequest httpRequest = HttpRequest.from(in);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse httpResponse = HttpResponse.createDefaultHttpResponse(byteArrayOutputStream);

        User user = new User("javajigi", "password", "박재성", "javajigi@slipp.net");
        userRepository.addUser(user);

        controller.service(httpRequest, httpResponse);

        String response = byteArrayOutputStream.toString();

        assertAll(
                () -> assertThat(response).contains("HTTP/1.1 302 Found"),
                () -> assertThat(response).contains("Location: /user/login")
        );
    }
}
