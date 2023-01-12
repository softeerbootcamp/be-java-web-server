import http.HttpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.HttpRequestUtils;
import util.HttpResponseUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpResponseUtilsTest {
    String basePath = "./src/main/resources";
    String htmlFilePath = "/templates";
    String staticFilePath = "/static";
    @Test
    @DisplayName("contentType을 입력 받고 알맞는 file path 반환")
    public void makeFilePath() {
        String path = HttpResponseUtils.makeFilePath("text/html");
        assertThat(path).isEqualTo(basePath + htmlFilePath);
        path = HttpResponseUtils.makeFilePath("text/css");
        assertThat(path).isEqualTo(basePath + staticFilePath);
    }

    @Test
    @DisplayName("httprequest 속의 uri와 파일 경로를 입력받아서 http response body 생성")
    public void makeBody() throws IOException {
        String httpUri = "/user/form.html";
        String filePath = basePath + htmlFilePath;
        assertThat(HttpResponseUtils.makeBody(httpUri, filePath))
                .isEqualTo(Files.readAllBytes(new File(filePath + httpUri).toPath()));
    }
}
