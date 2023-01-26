package http.request;

import http.request.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
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
        HttpUri httpUri = requestLine.getHttpUri();
        assertEquals(HttpMethod.GET, requestLine.getHttpMethod());
        assertEquals("/", httpUri.getPath());
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
        HttpUri httpUri = requestLine.getHttpUri();
        assertEquals(HttpMethod.GET, requestLine.getHttpMethod());
        assertEquals("/search", httpUri.getPath());
        assertEquals("HTTP/1.1", requestLine.getVersion());

        Map<String, String> queryParams = httpUri.getQueryParameters().getParameters();
        assertEquals("test", queryParams.get("q"));
        assertEquals("asc", queryParams.get("sort"));

        RequestHeader requestHeader = request.getRequestHeader();
        assertEquals("www.example.com", requestHeader.getHeader("Host"));
        assertEquals("application/json", requestHeader.getHeader("Accept"));
    }

    @DisplayName("Post 회원가입 테스트")
    @Test
    public void POST_회원가입_테스트() throws IOException {
        String input = "POST /user/create HTTP/1.1\n" +
                        "Host: localhost:8080\n" +
                        "Content-Length: 69\n" +
                        "Content-Type: application/x-www-form-urlencoded\n" +
                        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9," +
                        "image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\n" +
                        "\n" +
                        "userId=askldnf&password=lskandf&name=rlkasndfl&email=ksadnf%40askndlf";
        BufferedReader br = new BufferedReader(new StringReader(input));
        HttpRequest request = HttpRequest.from(br);
        RequestLine requestLine = request.getRequestLine();
        HttpUri httpUri = requestLine.getHttpUri();

        assertEquals(HttpMethod.POST, requestLine.getHttpMethod());
        assertEquals("/user/create", httpUri.getPath());
        assertEquals("HTTP/1.1", requestLine.getVersion());

        Map<String, String> requestBody = request.getRequestBody();
        assertEquals("askldnf", requestBody.get("userId"));
        assertEquals("lskandf", requestBody.get("password"));
        assertEquals("rlkasndfl", requestBody.get("name"));
        assertEquals("ksadnf@askndlf", requestBody.get("email"));

        RequestHeader requestHeader = request.getRequestHeader();
        assertEquals("69", requestHeader.getHeader("Content-Length"));
    }
}
