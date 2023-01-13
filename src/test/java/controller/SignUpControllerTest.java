package controller;

import http.common.HttpHeaders;
import http.common.HttpMethod;
import http.common.HttpStatus;
import http.common.URI;
import http.exception.MethodNotAllowException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SignupController Test")
public class SignUpControllerTest {

    @Test
    @DisplayName("execute() - 회원가입 성공 후 리다이렉트 테스트")
    void signUp() {
        // given
        SignUpController controller = new SignUpController();
        Map<String, String> user = Map.of(
                "userId", "sol",
                "password", "1234",
                "name", "sol",
                "email", "sol@sol.com");
        HttpRequest request = new HttpRequest(
                HttpMethod.GET,
                new URI("/user/create", user),
                new HttpHeaders());
        HttpResponse response = new HttpResponse();

        // when
        controller.execute(request, response);

        // then
        String redirect = response.getHeaders().getValue("Location");
        HttpStatus status = response.getStatus();
        assertAll(
                () -> assertEquals("/user/login.html", redirect),
                () -> assertEquals(HttpStatus.FOUND, status)
        );
    }

    @Test
    @DisplayName("execute() - 지원하지 않는 메서드(POST) 테스트")
    void signUpToNotAllowMethod() {
        // given
        SignUpController controller = new SignUpController();
        Map<String, String> user = Map.of(
                "userId", "sol",
                "password", "1234",
                "name", "sol",
                "email", "sol@sol.com");
        HttpRequest request = new HttpRequest(
                HttpMethod.POST,
                new URI("/user/create", user),
                new HttpHeaders());
        HttpResponse response = new HttpResponse();

        // when
        MethodNotAllowException exception = assertThrows(
                MethodNotAllowException.class,
                () -> controller.execute(request, response));
        // then
        assertEquals("Method Not Allowed", exception.getMessage());
    }
}
