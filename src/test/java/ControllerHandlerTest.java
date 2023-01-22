import controller.Controller;
import controller.ControllerHandler;
import controller.UserController;
import http.request.HttpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ControllerHandlerTest {

    @DisplayName("ViewController Return Test")
    @Test
    public void ViewController_Return_Test() throws IOException {
        String input = "GET /index.html HTTP/1.1\n" +
                "Host: www.example.com\n" +
                "Accept: text/html\n" +
                "\n";
        BufferedReader br = new BufferedReader(new StringReader(input));
        HttpRequest httpRequest = HttpRequest.from(br);

        Controller result = ControllerHandler.findController(httpRequest);
        assertTrue(result instanceof StaticController);
    }

    @DisplayName("UserController Return Test")
    @Test
    public void UserController_Return_Test() throws IOException {
        String input = "GET /user?q=test&sort=asc HTTP/1.1\n" +
                "Host: www.example.com\n" +
                "Accept: */*\n" +
                "\n";
        BufferedReader br = new BufferedReader(new StringReader(input));
        HttpRequest httpRequest = HttpRequest.from(br);

        Controller result = ControllerHandler.findController(httpRequest);
        assertTrue(result instanceof UserController);
    }
}
