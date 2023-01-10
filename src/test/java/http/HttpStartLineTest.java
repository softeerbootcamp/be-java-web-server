package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class HttpStartLineTest {

    @Test
    @DisplayName("성공 테스트")
    void createHttpStartLine() {
        String startLine = "GET /index.html HTTP/1.1";
        HttpStartLine httpStartLine = HttpStartLine.from(startLine);

        assertAll(
                () -> assertThat(httpStartLine.getMethod().toString()).hasToString("GET"),
                () -> assertThat(httpStartLine.getUri().getPath()).isEqualTo("/index.html"),
                () -> assertThat(httpStartLine.getHttpVersion()).isEqualTo("HTTP/1.1")
        );
    }

    @Test
    @DisplayName("실패 테스트")
    void createStartLineException() {
        assertThatThrownBy(() -> HttpStartLine.from("GET"))
                .isInstanceOf(ArrayIndexOutOfBoundsException.class);
    }

}
