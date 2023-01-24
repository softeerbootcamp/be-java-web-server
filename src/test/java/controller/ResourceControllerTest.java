package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResourceControllerTest {
    @DisplayName("Html이 아닌 Resoucre 테스트")
    @Test
    public void NonHtml() throws Exception {
        String input = "GET /css/bootstrap.min.css HTTP/1.1" + System.lineSeparator()  +
                "Host: localhost:8080" + System.lineSeparator()  +
                "Connection: keep-alive" + System.lineSeparator()  +
                "Accept: text/css" + System.lineSeparator() +
                System.lineSeparator();
        BufferedReader br = new BufferedReader(new StringReader(input));
        HttpRequest httpRequest = HttpRequest.from(br);
        HttpResponse httpResponse = ControllerHandler.handle(httpRequest);

        assertAll(
                () -> assertEquals(HttpStatus.OK, httpResponse.getHttpStatus()),
                () -> assertEquals("text/css", httpResponse.getContentType())
        );
    }


}
