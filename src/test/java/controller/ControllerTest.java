package controller;


import http.request.HttpRequest;
import http.response.HttpResponse;
import org.junit.jupiter.api.Test;
import webserver.ControllerMapper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static utils.FileIoUtils.load404ErrorFile;

public class ControllerTest {

    @Test
    public void testUserCreateHandle() throws IOException, URISyntaxException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        HttpResponse response = HttpResponse.createDefaultHttpResponse(outputStream);
        String input = "GET /user/create HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "User-Agent: curl/7.68.0\r\n" +
                "Accept: */*\r\n" +
                "\r\n";

        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        HttpRequest request = HttpRequest.from(in);

        Controller controller = ControllerMapper.findController(request);
        controller.service(request, response);

        Map<String, String> headers = response.getHeaders();

        assertAll(
                () -> assertThat("302").isEqualTo(response.getStatusCode()),
                () -> assertThat(headers.containsKey("Location")).isTrue(),
                () -> assertThat(headers.get("Location")).isEqualTo("/index.html")

        );
    }

    @Test
    public void testResourceHandle() throws IOException, URISyntaxException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        HttpResponse response = HttpResponse.createDefaultHttpResponse(outputStream);
        String input = "GET /index.html HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "User-Agent: curl/7.68.0\r\n" +
                "Accept: */*\r\n" +
                "\r\n";

        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        HttpRequest request = HttpRequest.from(in);

        Controller controller = ControllerMapper.findController(request);
        controller.service(request, response);

        Map<String, String> headers = response.getHeaders();

        assertAll(
                () -> assertThat("200").isEqualTo(response.getStatusCode()),
                () -> assertThat(headers.containsKey("Content-Type")).isTrue(),
                () -> assertThat(headers.containsKey("Content-Length")).isTrue()
        );
    }

    @Test
    public void testNotFoundController() throws IOException, URISyntaxException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        HttpResponse response = HttpResponse.createDefaultHttpResponse(outputStream);
        String input = "GET /user/love HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "User-Agent: curl/7.68.0\r\n" +
                "Accept: */*\r\n" +
                "\r\n";

        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        HttpRequest request = HttpRequest.from(in);

        Controller controller = ControllerMapper.findController(request);
        assertThat(controller).isNull();

        response.do404(load404ErrorFile());
        assertThat(response.getStatusCode()).isEqualTo("404");
    }

    @Test
    public void testNotFoundFile() throws IOException, URISyntaxException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        HttpResponse response = HttpResponse.createDefaultHttpResponse(outputStream);
        String input = "GET /nothing.html HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "User-Agent: curl/7.68.0\r\n" +
                "Accept: */*\r\n" +
                "\r\n";

        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        HttpRequest request = HttpRequest.from(in);

        Controller controller = ControllerMapper.findController(request);
        controller.service(request, response);

        assertThat(response.getStatusCode()).isEqualTo("404");
    }

}
