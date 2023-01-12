import http.HttpHeader;
import http.HttpRequestLine;
import http.HttpUri;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.HttpRequestUtils;
import util.HttpResponseUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestUtilsTest {
    String sampleRequestLine = "GET /index.html HTTP/1.1";
    @Test
    @DisplayName("RequestLine을 잘 쪼개서 RequestLine 생성자 인자로 잘 넣어 주는지")
    public void getRequestLine(){
        HttpRequestLine httpRequestLine = HttpRequestUtils.getRequestLine(sampleRequestLine);

        assertThat(httpRequestLine).usingRecursiveComparison().
                isEqualTo(new HttpRequestLine("GET", new HttpUri("/index.html"), "HTTP/1.1"));
    }

    String queryString = "userId=jhchoi57&" +
            "password=12349865&" +
            "name=%EC%B5%9C%EC%A3%BC%ED%98%95&" +
            "email=jhchoi57%40gmail.com";

    @Test
    @DisplayName("입력받은 queryString을 파싱해서 map에 잘 넣는지")
    public void parseQueryString(){
        Map<String, String> requestParamsMap = HttpRequestUtils.parseQueryString(queryString);
        assertThat(requestParamsMap.get("userId")).isEqualTo("jhchoi57");
        assertThat(requestParamsMap.get("password")).isEqualTo("12349865");
        assertThat(requestParamsMap.get("name")).isEqualTo("최주형");
        assertThat(requestParamsMap.get("email")).isEqualTo("jhchoi57@gmail.com");
    }

}
