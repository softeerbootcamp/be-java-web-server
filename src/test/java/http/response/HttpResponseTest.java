package http.response;

import controller.ControllerHandler;
import http.request.HttpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpResponseTest {
    @DisplayName("쿼리 파라미터가 없는 경우")
    @Test
    public void 쿼리_파라미터가_없는_경우() throws Exception {
        String input = "GET /index.html HTTP/1.1\n" +
                "Host: www.example.com\n" +
                "Accept: text/html\n" +
                "\n";
        BufferedReader br = new BufferedReader(new StringReader(input));
        HttpRequest request = HttpRequest.from(br);
        HttpResponse response = ControllerHandler.handle(request);

        assertEquals("HTTP/1.1", request.getHttpVersion());
        assertEquals("text/html", response.getContentType());
        assertEquals(HttpStatus.OK, response.getHttpStatus());
        assertEquals(new HashMap<>(), response.getHeaders());
    }

    @DisplayName("쿼리 파라미터가 있는 경우")
    @Test
    public void 쿼리_파라미터가_있는_경우() throws Exception {
        String input = "GET /user?q=test&sort=asc HTTP/1.1\n" +
                "Host: www.example.com\n" +
                "Accept: application/json\n" +
                "\n";
        BufferedReader br = new BufferedReader(new StringReader(input));
        HttpRequest request = HttpRequest.from(br);
        HttpResponse response = ControllerHandler.handle(request);

        assertEquals("HTTP/1.1", request.getHttpVersion());
        assertEquals("", response.getContentType());
        assertEquals(HttpStatus.FOUND, response.getHttpStatus());
        assertEquals("/index.html", response.getHeaders().get("Location"));
    }

    @DisplayName("Post 회원가입 테스트")
    @Test
    public void POST_회원가입_테스트() throws Exception {
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
        HttpResponse response = ControllerHandler.handle(request);
        assertEquals("HTTP/1.1", request.getHttpVersion());
        assertEquals("", response.getContentType());
        assertEquals(HttpStatus.FOUND, response.getHttpStatus());
        assertEquals("/index.html", response.getHeaders().get("Location"));
    }
}
