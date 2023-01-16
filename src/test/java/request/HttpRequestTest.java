package request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HttpRequestTest {

    @DisplayName("요청 template 경로 테스트")
    @Test
    void 경로_테스트() {
        RequestStartLine requestLine = RequestStartLine.of("GET /index.html HTTP/1.1");
        Map<String, String> headerFields = new HashMap<>();
        headerFields.put("Host", "localhost:8080");
        headerFields.put("Connection", "keep-alive");
        headerFields.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        StringBuilder header = new StringBuilder();
        header.append(headerFields);

        HttpRequest httpRequest = new HttpRequest(requestLine, header.toString());

        String path = httpRequest.getPath();

        assertThat(path).isEqualTo("./templates/index.html");
    }
}
