import http.request.HttpRequestLine;
import http.HttpUri;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.HttpRequestUtils;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestUtilsTest {
    String sampleRequestLine = "GET /index.html HTTP/1.1";
    @Test
    @DisplayName("RequestLine 입력받기 테스트")
    public void readRequestLineTest(){
        HttpRequestLine httpRequestLine = HttpRequestUtils.readRequestLine(sampleRequestLine);

        assertThat(httpRequestLine).usingRecursiveComparison().
                isEqualTo(new HttpRequestLine("GET", new HttpUri("/index.html"), "HTTP/1.1"));
    }

    String queryString = "userId=jhchoi57&" +
            "password=12349865&" +
            "name=%EC%B5%9C%EC%A3%BC%ED%98%95&" +
            "email=jhchoi57%40gmail.com";

    @Test
    @DisplayName("queryString 파싱 테스트")
    public void parseQueryStringTest(){
        Map<String, String> requestParamsMap = HttpRequestUtils.parseQueryString(queryString);
        assertThat(requestParamsMap.get("userId")).isEqualTo("jhchoi57");
        assertThat(requestParamsMap.get("password")).isEqualTo("12349865");
        assertThat(requestParamsMap.get("name")).isEqualTo("최주형");
        assertThat(requestParamsMap.get("email")).isEqualTo("jhchoi57@gmail.com");
    }

}
