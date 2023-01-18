import db.Database;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpStatus;
import org.junit.jupiter.api.Test;
import service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    private final String input = "POST /user/create HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Content-Length: 69\n" +
            "Content-Type: application/x-www-form-urlencoded\n" +
            "Accept: text/html,application/xhtml+xml,application/xml;q=0.9," +
            "image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\n" +
            "\n" +
            "userId=askldnf&password=lskandf&name=rlkasndfl&email=ksadnf%40askndlf";
    private UserService userService = new UserService();

    @Test
    public void testCreate() throws IOException {
        BufferedReader br = new BufferedReader(new StringReader(input));
        HttpRequest httpRequest = HttpRequest.from(br);
        HttpResponse httpResponse = userService.create(httpRequest);
        assertEquals(HttpStatus.FOUND, httpResponse.getHttpStatus());
        assertEquals("/index.html", httpResponse.getHeaders().get("Location"));
        assertTrue(Objects.nonNull(Database.findUserById("askldnf")));

    }

    @Test
    public void testLoginSuccess() throws IOException {
        BufferedReader br = new BufferedReader(new StringReader(input));
        HttpRequest httpRequest = HttpRequest.from(br);
        HttpResponse httpResponse = userService.login(httpRequest);
        assertEquals(HttpStatus.FOUND, httpResponse.getHttpStatus());
        assertEquals("/index.html", httpResponse.getHeaders().get("Location"));
        assertNotNull(httpResponse.getHeaders().get("Set-Cookie"));
    }

}