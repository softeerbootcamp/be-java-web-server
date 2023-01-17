package http.request;

import http.Uri;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class HttpRequestTest {

    @Test
    public void testGetFrom() throws IOException {
        String input = "GET /test HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "User-Agent: curl/7.68.0\r\n" +
                "Accept: */*\r\n" +
                "\r\n";

        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        HttpRequest request = HttpRequest.from(in);

        HttpStartLine startLine = request.getStartLine();

        Uri uri = Uri.from("/test");

        Map<String, String> headers = request.getHttpHeaders();
        System.out.println(headers.toString());

        assertAll(
                () -> assertThat(startLine.getMethod()).isEqualTo(HttpMethod.GET),
                () -> assertThat(startLine.getUri().getPath()).isEqualTo(uri.getPath()),
                () -> assertThat(headers.containsKey("Host")).isTrue(),
                () -> assertThat(headers.get("Host")).isEqualTo("localhost:8080"),
                () -> assertThat(headers.containsKey("User-Agent")).isTrue(),
                () -> assertThat(headers.get("User-Agent")).isEqualTo("curl/7.68.0"),
                () -> assertThat(headers.containsKey("Accept")).isTrue(),
                () -> assertThat(headers.get("Accept")).isEqualTo("*/*")
        );
    }

    @Test
    public void testPostFrom() throws IOException {
        String input = "POST /test HTTP/1.1\r\n" +
                "Content-Length: 67\r\n" +
                "Host: localhost:8080\r\n" +
                "User-Agent: curl/7.68.0\r\n" +
                "Accept: */*\r\n" +
                "\r\n" +
                "userId=kgstiger&password=password&name=김강산&email=kgstiger@slipp.net";

        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        HttpRequest request = HttpRequest.from(in);

        HttpStartLine startLine = request.getStartLine();

        Uri uri = Uri.from("/test");

        Map<String, String> headers = request.getHttpHeaders();

        Map<String, String> parameters = request.getParameters();

        assertAll(
                () -> assertThat(startLine.getMethod()).isEqualTo(HttpMethod.POST),
                () -> assertThat(startLine.getUri().getPath()).isEqualTo(uri.getPath()),
                () -> assertThat(headers.containsKey("Content-Length")).isTrue(),
                () -> assertThat(headers.get("Content-Length")).isEqualTo("67"),
                () -> assertThat(headers.containsKey("Host")).isTrue(),
                () -> assertThat(headers.get("Host")).isEqualTo("localhost:8080"),
                () -> assertThat(headers.containsKey("User-Agent")).isTrue(),
                () -> assertThat(headers.get("User-Agent")).isEqualTo("curl/7.68.0"),
                () -> assertThat(headers.containsKey("Accept")).isTrue(),
                () -> assertThat(headers.get("Accept")).isEqualTo("*/*"),
                () -> assertThat(parameters.containsKey("userId")).isTrue(),
                () -> assertThat(parameters.get("userId")).isEqualTo("kgstiger"),
                () -> assertThat(parameters.containsKey("password")).isTrue(),
                () -> assertThat(parameters.get("password")).isEqualTo("password"),
                () -> assertThat(parameters.containsKey("name")).isTrue(),
                () -> assertThat(parameters.get("name")).isEqualTo("김강산"),
                () -> assertThat(parameters.containsKey("email")).isTrue(),
                () -> assertThat(parameters.get("email")).isEqualTo("kgstiger@slipp.net")
        );
    }


}
