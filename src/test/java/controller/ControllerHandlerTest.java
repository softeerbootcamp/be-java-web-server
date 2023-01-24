package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ControllerHandlerTest {

    @DisplayName("ResourceController Return Test")
    @Test
    public void ResourceController_Return_Test() throws IOException {
        String input = "GET /index.html HTTP/1.1" + System.lineSeparator() +
                "Host: www.example.com" + System.lineSeparator() +
                "Accept: text/html" + System.lineSeparator() +
                System.lineSeparator();
        BufferedReader br = new BufferedReader(new StringReader(input));
        HttpRequest httpRequest = HttpRequest.from(br);

        Controller result = ControllerHandler.findController(httpRequest.getUri());
        assertTrue(result instanceof ResourceController);
    }

    @DisplayName("UserController Return Test")
    @Test
    public void UserController_Return_Test() throws IOException {
        String input = "GET /user?q=test&sort=asc HTTP/1.1" + System.lineSeparator() +
                "Host: www.example.com" + System.lineSeparator() +
                "Accept: */*" + System.lineSeparator() +
                System.lineSeparator();
        BufferedReader br = new BufferedReader(new StringReader(input));
        HttpRequest httpRequest = HttpRequest.from(br);

        Controller result = ControllerHandler.findController(httpRequest.getUri());
        assertTrue(result instanceof UserController);
    }

    @DisplayName("잘못된 Url 입력시 404 반환 테스트")
    @Test
    public void Controller_Handle_NotFound_Test() throws Exception {
        String input = "GET /users/create.html HTTP/1.1" + System.lineSeparator() +
                "Host: localhost:8080" + System.lineSeparator()  +
                "Connection: keep-alive" + System.lineSeparator()  +
                "Accept: text/html" + System.lineSeparator() +
                System.lineSeparator();
        BufferedReader br = new BufferedReader(new StringReader(input));
        HttpRequest httpRequest = HttpRequest.from(br);
        HttpResponse httpResponse = ControllerHandler.handle(httpRequest);
        assertEquals(HttpStatus.NOT_FOUND, httpResponse.getHttpStatus());
    }
}
