import db.Database;
import http.response.HttpResponse;
import model.User;
import org.junit.jupiter.api.Test;
import service.LogInService;
import util.HttpRequestUtils;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class LogInServiceTest {
    @Test
    void 로그인서비스_성공_테스트() {
        // given
        Database.addUser(new User("testUserId", "testPassword", "testName", "testEmail@naver.com"));
        String body = "userId=testUserId&password=testPassword";
        Map<String, String> params = HttpRequestUtils.parseQueryString(body);

        // when
        HttpResponse successResponse = LogInService.service(params, "HTTP/1.1");

        // then
        assertThat(successResponse.getHeaders().getLocation()).isEqualTo("/index.html");
    }

    @Test
    void 로그인서비스_실패_테스트() {
        // given
        Database.addUser(new User("testUserId", "testPassword", "testName", "testEmail@naver.com"));
        String body = "userId=testfdadUserId&password=testasdPassword";
        Map<String, String> params = HttpRequestUtils.parseQueryString(body);

        // when
        HttpResponse FailResponse = LogInService.service(params, "HTTP/1.1");

        // then
        assertThat(FailResponse.getHeaders().getLocation()).isEqualTo("/user/login_failed.html");

    }
}
