package http.util;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

// TODO: HttpBodyParser라기엔 Content-Type : application/x-www-form-urlencoded 형식만 지원하는 상황.
// TODO: 이 또한 HttpBodyParser라는 인터페이트를 구현하는 방식으로 리팩토링 가능...
// TODO: ...하지만 Content-Type마다 파싱 응답 타입이 모두 다르기에 적용한다면 비교적 큰 수정이 예상됨.*/
public class HttpFormBodyParser {
    private HttpFormBodyParser() {}

    public static Map<String, String> parse(String stringBody) {
        Map<String, String> data = new HashMap<>();
        String[] dataArr = stringBody.replaceAll("\n", "").split("&");

        for (String el: dataArr) {
            String[] strings = el.split("=");
            data.put(strings[0], "");

            if (strings.length == 2) {
                data.put(strings[0], URLDecoder.decode(strings[1], StandardCharsets.UTF_8));
            }
        }

        return data;
    }
}
