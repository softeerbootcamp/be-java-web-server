import http.HttpHeader;
import org.junit.jupiter.api.Test;
import util.HttpResponseUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpResponseUtilsTest {

    @Test
    void 바디_생성_테스트() throws IOException {
        // given
        String filePath = "./src/main/resources/templates/index.html";
        // when
        byte[] body = HttpResponseUtils.makeBody(filePath);
        // then
        assertThat(body.length).isEqualTo(Files.readAllBytes(new File(filePath).toPath()).length);
    }

    @Test
    void OK헤더_생성_테스트() {
        // given
        String contentType = "text/html";
        int bodyLength = 1024;
        // when
        HttpHeader header = HttpResponseUtils.makeResponse200Header(contentType, bodyLength);
        // then
        assertThat(header.toString()).isEqualTo(
                "Content-Length: 1024" + System.lineSeparator() +
                        "Content-Type: text/html;charset=utf-8" + System.lineSeparator() + System.lineSeparator());
    }

    @Test
    void FOUND헤더_생성_테스트() {
        // given
        String destination = "/index.html";
        // when
        HttpHeader header = HttpResponseUtils.makeResponse302Header(destination);
        // then
        assertThat(header.toString()).isEqualTo(
                "Location: /index.html" + System.lineSeparator() + System.lineSeparator());
    }


}
