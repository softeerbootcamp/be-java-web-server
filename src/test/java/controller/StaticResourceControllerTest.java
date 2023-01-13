package controller;

import http.common.*;
import http.exception.MethodNotAllowException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("StaticResourceController Test")
public class StaticResourceControllerTest {

    @Test
    @DisplayName("execute() - 정상 리드 테스트")
    void execute() {
        // given
        StaticResourceController controller = new StaticResourceController();
        HttpRequest request = new HttpRequest(
                HttpMethod.GET,
                new URI("/index.html", Map.of()),
                new HttpHeaders()
        );
        HttpResponse response = new HttpResponse();

        // when
        controller.execute(request, response);

        // then
        HttpBody body = response.getBody();
        HttpHeaders headers = response.getHeaders();
        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatus()),
                () -> assertEquals(MediaType.TEXT_HTML.getType(), headers.getValue("Content-Type")),
                () -> assertEquals(6902, body.size())
        );
    }

    @Test
    @DisplayName("execute() - 지원하지 않는 메서드(POST) 테스트")
    void executeWhenNotAllowMethod() {
        // given
        StaticResourceController controller = new StaticResourceController();
        HttpRequest request = new HttpRequest(
                HttpMethod.POST,
                new URI("/index.html", Map.of()),
                new HttpHeaders()
        );
        HttpResponse response = new HttpResponse();

        // when
        MethodNotAllowException exception = assertThrows(
                MethodNotAllowException.class,
                () -> controller.execute(request, response));

        // then
        assertEquals("Method Not Allowed", exception.getMessage());
    }
}
