package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpHeadersTest {

    @Test
    public void testFrom() {
        String[] lines = {"Content-Type: text/html", "Content-Length: 1024"};
        HttpHeader headers = HttpHeader.from(lines);

        assertThat(headers.getValue("Content-Type")).isEqualTo("text/html");
        assertThat(headers.getValue("Content-Length")).isEqualTo("1024");
    }

    @Test
    public void testAddHeader() {
        HttpHeader headers = HttpHeader.createDefaultHeaders();

        headers.addHeader("Content-Type", "text/html");
        headers.addHeader("Content-Length", "1024");

        assertThat(headers.getValue("Content-Type")).isEqualTo("text/html");
        assertThat(headers.getValue("Content-Length")).isEqualTo("1024");
    }

    @Test
    public void testGetHeaders() {
        HttpHeader headers = HttpHeader.createDefaultHeaders();

        headers.addHeader("Content-Type", "text/html");
        headers.addHeader("Content-Length", "1024");

        assertThat(headers.getHeaders())
                .containsEntry("Content-Type", "text/html")
                .containsEntry("Content-Length", "1024");
    }
}
