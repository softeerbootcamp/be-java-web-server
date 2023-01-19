import http.response.HttpResponse;
import model.User;
import org.junit.jupiter.api.Test;
import service.ListService;

import static org.assertj.core.api.Assertions.assertThat;

public class ListServiceTest {
    // Given
    User logInUser = new User("user1", "password", "홍길동", "jasd@email.com");
    String filePath = "src/main/resources/templates/user/list.html";
    String httpVersion = "HTTP/1.1";
    String contentType = "text/html";

    @Test
    public void 로그인상태_ListService_테스트() {
        // When
        HttpResponse response = ListService.logInListService(logInUser, filePath, httpVersion, contentType);

        // Then
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    public void 로그인상태가아닐때_ListService_테스트() {
        // When
        HttpResponse response = ListService.service(null, filePath, httpVersion, contentType);

        // Then
        assertThat(response.getHeaders().getLocation()).isEqualTo("/user/login.html");
    }
}
