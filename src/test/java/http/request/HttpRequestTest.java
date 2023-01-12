package http.request;

import http.Uri;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HttpRequestTest {

    @Test
    public void testFrom() throws IOException {
        String input = "GET /test HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "User-Agent: curl/7.68.0\r\n" +
                "Accept: */*\r\n" +
                "\r\n";

        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        HttpRequest request = HttpRequest.from(in);

        HttpStartLine startLine = request.getStartLine();
        assertEquals(HttpMethod.GET, startLine.getMethod());

        Uri uri = Uri.from("/test");
        assertEquals(uri.getPath(), startLine.getUri().getPath());

        Map<String, String> headers = request.getHttpHeaders();
        System.out.println(headers.toString());
        assertTrue(headers.containsKey("Host"));
        assertEquals("localhost:8080", headers.get("Host"));
        assertTrue(headers.containsKey("User-Agent"));
        assertEquals("curl/7.68.0", headers.get("User-Agent"));
        assertTrue(headers.containsKey("Accept"));
        assertEquals("*/*", headers.get("Accept"));
    }
}
