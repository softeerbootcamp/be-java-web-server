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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.SQLException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ResourceControllerTest {

    private final SessionRepository sessionRepository = new SessionRepository();
    private final SessionService sessionService = new SessionService(sessionRepository);
    private final UserRepository userRepository = new UserRepository();

    Controller controller = new ResourceController(sessionService);

    @Test
    @DisplayName("로그인 상태에서 로그인 버튼이 사라지고 유저 이름이 표시되는지 테스트")
    void doGetWithLogin() throws IOException, FileNotFoundException, URISyntaxException, SQLException {
        String request = "GET /index.html HTTP/1.1" + System.lineSeparator() +
                "Host: localhost:8080" + System.lineSeparator() +
                "Cookie: JSESSIONID=123;" + System.lineSeparator();

        InputStream in = new ByteArrayInputStream(request.getBytes());
        HttpRequest httpRequest = HttpRequest.from(in);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse httpResponse = HttpResponse.createDefaultHttpResponse(byteArrayOutputStream);

        User user = User.of("javajigi", "password", "박재성", "javajigi@slipp.net");

        userRepository.addUser(user);

        Session session = Session.of("123","javajigi","박재성");
        sessionRepository.addSession("123", session);

        controller.service(httpRequest, httpResponse);

        String response = byteArrayOutputStream.toString();

        assertAll(
                () -> assertThat(response).contains("HTTP/1.1 200 OK"),
                () -> assertThat(response).contains("Content-Type: text/html;charset=utf-8"),
                () -> assertThat(response).contains("<li><a>박재성</a></li>"),
                () -> assertThat(response).doesNotContain("<li><a href=\"user/login.html\" role=\"button\">로그인</a></li>")
        );
        userRepository.deleteById("javajigi");
    }

    @Test
    @DisplayName("비 로그인 상태에서 기본 /index.html인지 테스트")
    void doGetWithoutLogin() throws IOException, FileNotFoundException, URISyntaxException {
        String request = "GET /index.html HTTP/1.1" + System.lineSeparator() +
                "Host: localhost:8080" + System.lineSeparator();

        InputStream in = new ByteArrayInputStream(request.getBytes());
        HttpRequest httpRequest = HttpRequest.from(in);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse httpResponse = HttpResponse.createDefaultHttpResponse(byteArrayOutputStream);

        controller.service(httpRequest, httpResponse);

        String response = byteArrayOutputStream.toString();

        assertAll(
                () -> assertThat(response).contains("HTTP/1.1 200 OK"),
                () -> assertThat(response).contains("Content-Type: text/html;charset=utf-8"),
                () -> assertThat(response).contains("<li><a href=\"user/login.html\" role=\"button\">로그인</a></li>")

        );

    }

    @Test
    @DisplayName("잘못된 path가 주어졌을 때 404에러 페이지를 response 하는지 테스트")
    void do404Error() throws IOException, FileNotFoundException, URISyntaxException {
        String request = "GET /index HTTP/1.1" + System.lineSeparator() +
                "Host: localhost:8080" + System.lineSeparator();

        InputStream in = new ByteArrayInputStream(request.getBytes());
        HttpRequest httpRequest = HttpRequest.from(in);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse httpResponse = HttpResponse.createDefaultHttpResponse(byteArrayOutputStream);

        controller.service(httpRequest, httpResponse);

        String response = byteArrayOutputStream.toString();

        assertAll(
                () -> assertThat(response).contains("HTTP/1.1 404 Not Found"),
                () -> assertThat(response).contains("<div class=\"text\"></div><span>Ooops...</span><br>page not found")
        );
    }
}
