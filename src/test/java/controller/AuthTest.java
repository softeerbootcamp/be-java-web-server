package controller;

import http.common.HttpHeaders;
import http.common.HttpMethod;
import http.common.URL;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Auth Test")
public class AuthTest {

    @Test
    @DisplayName("auth - 세션이 없는 경우")
    void authWithNoSession() {
        // given
        Auth auth = new Auth() {};
        HttpRequest request = new HttpRequest(
                HttpMethod.GET,
                new URL("/test", Map.of()),
                new HttpHeaders(), Map.of()
        );

        HttpResponse response = new HttpResponse(null);

        // when
        boolean accessible = auth.auth(request, response);

        // then
        assertAll(
                () -> assertFalse(accessible),
                () -> assertEquals("/user/login.html", response.getHeaders().getValue("Location"))
        );
    }
}
