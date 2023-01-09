import org.junit.jupiter.api.Test;
import util.HttpRequestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestUtilsTest {
    @Test
    public void getUrl(){
        String url = HttpRequestUtils.getUrl("GET /index.html HTTP/1.1");
        assertThat(url).isEqualTo("/index.html");
    }

    @Test
    public void parseQueryString(){
        Map<String, String> requestParamsMap = HttpRequestUtils.parseQueryString("userId=jhchoi57&password=12349865&name=%EC%B5%9C%EC%A3%BC%ED%98%95&email=jhchoi57%40gmail.com");
        assertThat(requestParamsMap.get("userId")).isEqualTo("jhchoi57");
        assertThat(requestParamsMap.get("password")).isEqualTo("12349865");
        assertThat(requestParamsMap.get("name")).isEqualTo("최주형");
        assertThat(requestParamsMap.get("email")).isEqualTo("jhchoi57@gmail.com");

    }

}
