package controller;


import http.request.HttpRequest;
import http.response.HttpResponse;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ControllerFactoryTest {
    @Test
    public void testHandle() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        HttpResponse response = new HttpResponse(outputStream);
        String input = "GET /index.html HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "User-Agent: curl/7.68.0\r\n" +
                "Accept: */*\r\n" +
                "\r\n";

        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        HttpRequest request = HttpRequest.from(in);

        ControllerFactory.handle(request, response);
        Map<String, String> headers = response.getHeaders();

        assertAll(
                () -> assertThat("200").isEqualTo(response.getStatusCode()),
                () -> assertThat(headers.containsKey("Content-Type")).isTrue(),
                () -> assertThat(headers.containsKey("Content-Length")).isTrue()
        );
    }

    @Test
    public void testNotFoundController() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        HttpResponse response = new HttpResponse(outputStream);
        String input = "GET /user/love HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "User-Agent: curl/7.68.0\r\n" +
                "Accept: */*\r\n" +
                "\r\n";

        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        HttpRequest request = HttpRequest.from(in);

        ControllerFactory.handle(request, response);

        assertThat(response.getStatusCode()).isEqualTo("404");
    }

    @Test
    public void testNotFoundFile() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        HttpResponse response = new HttpResponse(outputStream);
        String input = "GET /nothing.html HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "User-Agent: curl/7.68.0\r\n" +
                "Accept: */*\r\n" +
                "\r\n";

        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        HttpRequest request = HttpRequest.from(in);

        ControllerFactory.handle(request, response);

        assertThat(response.getStatusCode()).isEqualTo("404");
    }

}
