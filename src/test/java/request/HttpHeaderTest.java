package request;

import http.HttpHeader;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpHeaderTest {
    @Test
    void test_create_HttpHeader() {
        HttpHeader header = HttpHeader.create();
        header.addHeader("Content-Type", "text/html");
        assertThat(header.getMessage()).contains("text/html");
    }
}
