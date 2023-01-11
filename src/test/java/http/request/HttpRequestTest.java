package http.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HttpRequestTest {
    @Test
    void testHttpRequestFrom() {
        String request = "GET /path HTTP/1.1\n" +
                "Host: www.example.com\n" +
                "User-Agent: MyApp\n" +
                "Accept: text/html\n";

        HttpRequest httpRequest = HttpRequest.from(request);

        assertEquals(HttpMethod.GET, httpRequest.getMethod());
        assertEquals("/path", httpRequest.getUri().getPath());
        assertEquals("HTTP/1.1", httpRequest.getStartLine().getHttpVersion());
        assertEquals("www.example.com", httpRequest.getHeaders().get("Host"));
        assertEquals("MyApp", httpRequest.getHeaders().get("User-Agent"));
        assertEquals("text/html", httpRequest.getHeaders().get("Accept"));
    }

}
