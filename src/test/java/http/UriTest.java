package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UriTest {

    @Test
    @DisplayName("Uri 생성 성공 테스트")
    void createUriTest() {
        String string = "/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        Uri uri = Uri.from(string);
        Map<String, String> expected = new HashMap<>();
        expected.put("userId", "javajigi");
        expected.put("password", "password");
        expected.put("name", "박재성");
        expected.put("email", "javajigi@slipp.net");

        assertAll(
                () -> assertThat(uri.getPath()).isEqualTo("/user/create"),
                () -> assertThat(uri.getParameters()).isEqualTo(expected)
        );

    }

}
