package http.exception;

import http.common.*;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.ResourceUtils;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("HttpExceptionHandler Test")
public class HttpExceptionHandlerTest {

    private final DataOutputStream mockDos = new DataOutputStream(new OutputStream() {
        @Override
        public void write(int b) {

        }
    });

    @Test
    @DisplayName("handle - 404 Not Found 예외 처리 테스트")
    void testNotFoundExceptionHandler() {
        // given
        HttpRequest request = new HttpRequest(
                HttpMethod.GET,
                new URI("/index", Map.of()),
                new HttpHeaders()
        );
        HttpResponse response = new HttpResponse(mockDos);
        String message = "리소스를 찾을 수 없습니다.";
        NotFoundException exception = new NotFoundException(message);
        byte[] bodyData = ResourceUtils.loadFileFromClasspath("/not_found.html");

        // when
        HttpExceptionHandler.handle(request, response, exception);
        // then
        assertAll(
                () -> assertEquals(HttpStatus.NOT_FOUND, response.getStatus()),
                () -> assertEquals(MediaType.TEXT_HTML.name(), response.getHeaders().getValue("Content-Type")),
                () -> assertEquals(String.valueOf(bodyData.length), response.getHeaders().getValue("Content-Length")),
                () -> assertEquals(bodyData.length, response.getBody().length)
        );
    }

    @Test
    @DisplayName("handle - defaultExceptionHandler 예외 처리 테스트")
    void testDefaultExceptionHandler() {
        // given
        HttpRequest request = new HttpRequest(
                HttpMethod.GET,
                new URI("/index", Map.of()),
                new HttpHeaders()
        );
        HttpResponse response = new HttpResponse(mockDos);
        String message = "서버에서 에러가 발생하였습니다.";
        InternalServerErrorException exception = new InternalServerErrorException(message);

        // when
        HttpExceptionHandler.handle(request, response, exception);

        // then
        assertAll(
                () -> assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus()),
                () -> assertEquals(MediaType.TEXT_PLAIN.name(), response.getHeaders().getValue("Content-Type")),
                () -> assertEquals(message.getBytes().length, response.getBody().length)
        );
    }
}
