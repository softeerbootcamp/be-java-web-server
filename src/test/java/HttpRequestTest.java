import http.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpRequestTest {
    @DisplayName("쿼리 파라미터가 없는 경우")
    @Test
    public void 쿼리_파라미터가_없는_경우() throws Exception {
        String input = "GET / HTTP/1.1\n" +
                "Host: www.example.com\n" +
                "Accept: text/html\n" +
                "\n";
        BufferedReader br = new BufferedReader(new StringReader(input));
        HttpRequest request = HttpRequest.from(br);

        RequestLine requestLine = request.getRequestLine();
        Uri uri = requestLine.getUri();
        assertEquals(Method.GET, requestLine.getMethod());
        assertEquals("/", uri.getPath());
        assertEquals("HTTP/1.1", requestLine.getVersion());

        RequestHeader requestHeader = request.getRequestHeader();
        assertEquals("www.example.com", requestHeader.getHeader("Host"));
        assertEquals("text/html", requestHeader.getHeader("Accept"));
    }

    @DisplayName("쿼리 파라미터가 있는 경우")
    @Test
    public void 쿼리_파라미터가_있는_경우() throws Exception {
        String input = "GET /search?q=test&sort=asc HTTP/1.1\n" +
                "Host: www.example.com\n" +
                "Accept: application/json\n" +
                "\n";
        BufferedReader br = new BufferedReader(new StringReader(input));
        HttpRequest request = HttpRequest.from(br);

        RequestLine requestLine = request.getRequestLine();
        Uri uri = requestLine.getUri();
        assertEquals(Method.GET, requestLine.getMethod());
        assertEquals("/search", uri.getPath());
        assertEquals("HTTP/1.1", requestLine.getVersion());

        Map<String, String> queryParams = uri.getQueryParameters().getParameters();
        assertEquals("test", queryParams.get("q"));
        assertEquals("asc", queryParams.get("sort"));

        RequestHeader requestHeader = request.getRequestHeader();
        assertEquals("www.example.com", requestHeader.getHeader("Host"));
        assertEquals("application/json", requestHeader.getHeader("Accept"));
    }

}
