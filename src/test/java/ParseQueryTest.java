import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.HttpUtils;

import java.util.Map;

public class ParseQueryTest {
    @Test
    @DisplayName("예시 파싱")
    public void parseQueryTest(){
        String query = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        Map<String, String> map = HttpUtils.parseQuerystring(query);
        Assertions.assertThat(map.get("userId")).isEqualTo("javajigi");
        Assertions.assertThat(map.get("password")).isEqualTo("password");
    }
}
