import controller.ControllerHandler;
import exception.ControllerNotFoundException;
import http.HttpRequest;
import http.RequestLine;
import exception.ResourceTypeNotFoundException;
import http.Uri;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class ExceptionTest {

    @Test
    void 컨트롤러_예외처리_테스트() throws IOException {
        String input = "GET /suerr/create?id=12&password=12 HTTP/1.1\n" +
                "Host: www.example.com\n" +
                "Accept: text/html\n" +
                "\n";
        BufferedReader br = new BufferedReader(new StringReader(input));
        HttpRequest request = HttpRequest.from(br);
        Assertions.assertThrows(ControllerNotFoundException.class, () -> ControllerHandler.findController(request));
    }

    @Test
    void 정적타입_예외처리_테스트() throws IOException {
        String input = "GET /index.html2 HTTP/1.1\n" +
                "Host: www.example.com\n" +
                "Accept: text/html\n" +
                "\n";
        BufferedReader br = new BufferedReader(new StringReader(input));
        HttpRequest request = HttpRequest.from(br);
        RequestLine requestLine = request.getRequestLine();
        Uri uri = requestLine.getUri();
        Assertions.assertThrows(ResourceTypeNotFoundException.class, uri::isEndWithResourceType);
    }
}
