package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpHeadersTest {

    @Test
    @DisplayName("Header 생성자 테스트")
    public void testFrom() {
        String[] lines = {"Content-Type: text/html", "Content-Length: 1024"};
        HttpHeader headers = HttpHeader.from(lines);

        assertThat(headers.getValue("Content-Type")).isEqualTo("text/html");
        assertThat(headers.getValue("Content-Length")).isEqualTo("1024");
    }

    @Test
    @DisplayName("Header를 정상적으로 추가하는지 테스트")
    public void testAddHeader() {
        HttpHeader headers = HttpHeader.createDefaultHeaders();

        headers.addHeader("Content-Type", "text/html");
        headers.addHeader("Content-Length", "1024");

        assertThat(headers.getValue("Content-Type")).isEqualTo("text/html");
        assertThat(headers.getValue("Content-Length")).isEqualTo("1024");
    }

    @Test
    @DisplayName("Header를 정상적으로 반환하는지 테스트")
    public void testGetHeaders() {
        HttpHeader headers = HttpHeader.createDefaultHeaders();

        headers.addHeader("Content-Type", "text/html");
        headers.addHeader("Content-Length", "1024");

        assertThat(headers.getHeaders())
                .containsEntry("Content-Type", "text/html")
                .containsEntry("Content-Length", "1024");
    }
}
