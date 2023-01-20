package controller;

import db.UserRepository;
import exception.FileNotFoundException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.junit.jupiter.api.Test;
import service.UserService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserCreateControllerTest {
    private final UserRepository userRepository = new UserRepository();
    private final UserService userService = new UserService(userRepository);

    Controller controller = new UserCreateController(userService);

    @Test
    void doPost() throws IOException, FileNotFoundException, URISyntaxException {
        String request =
                "POST /user/login HTTP/1.1" + System.lineSeparator() +
                        "Host: localhost:8080" + System.lineSeparator() +
                        "Connection: keep-alive" + System.lineSeparator() +
                        "Content-Length: 59" + System.lineSeparator() +
                        "Content-Type: application/x-www-form-urlencoded" + System.lineSeparator() +
                        "Accept: */*" + System.lineSeparator() +
                        System.lineSeparator() +
                        "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        InputStream in = new ByteArrayInputStream(request.getBytes());
        HttpRequest httpRequest = HttpRequest.from(in);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse httpResponse = HttpResponse.createDefaultHttpResponse(byteArrayOutputStream);

        controller.service(httpRequest, httpResponse);

        String response = byteArrayOutputStream.toString();

        assertAll(
                () -> assertThat(response).contains("HTTP/1.1 302 Found"),
                () -> assertThat(response).contains("/index.html")
        );

    }

}
