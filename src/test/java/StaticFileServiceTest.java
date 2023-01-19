import http.response.HttpResponse;
import org.junit.jupiter.api.Test;
import service.StaticFileService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class StaticFileServiceTest {
    @Test
    void 정적_파일_서비스_테스트(){
        // Given
        String filePath = "src/main/resources/templates/index.html";
        String httpVersion = "HTTP/1.1";
        String contentType = "text/html";

        // When
        HttpResponse response = StaticFileService.service(filePath, httpVersion, contentType);

        // Then
        assertThat(response.getBody()).isNotEmpty();
    }
}
