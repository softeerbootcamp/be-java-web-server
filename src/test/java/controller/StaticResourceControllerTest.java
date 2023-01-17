package controller;

import http.common.*;
import http.exception.MethodNotAllowException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("StaticResourceController Test")
public class StaticResourceControllerTest {

    private final DataOutputStream mockDos = new DataOutputStream(new OutputStream() {
        @Override
        public void write(int b) {

        }
    });

    @Test
    @DisplayName("execute() - 정상 리드 테스트")
    void execute() {
        // given
        StaticResourceController controller = new StaticResourceController();
        HttpRequest request = new HttpRequest(
                HttpMethod.GET,
                new URL("/index.html", Map.of()),
                new HttpHeaders()
        );
        HttpResponse response = new HttpResponse(mockDos);

        // when
        controller.execute(request, response);

        // then
        HttpHeaders headers = response.getHeaders();
        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatus()),
                () -> assertEquals(ContentType.TEXT_HTML.getType(), headers.getValue("Content-Type")),
                () -> assertEquals(6902, response.getBody().length)
        );
    }

    @Test
    @DisplayName("execute() - 지원하지 않는 메서드(POST) 테스트")
    void executeWhenNotAllowMethod() {
        // given
        StaticResourceController controller = new StaticResourceController();
        HttpRequest request = new HttpRequest(
                HttpMethod.POST,
                new URL("/index.html", Map.of()),
                new HttpHeaders()
        );
        HttpResponse response = new HttpResponse(mockDos);

        // when
        MethodNotAllowException exception = assertThrows(
                MethodNotAllowException.class,
                () -> controller.execute(request, response));

        // then
        assertEquals("Method Not Allowed", exception.getMessage());
    }
}
