package http.util;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

// TODO: HttpBodyParser라기엔 Content-Type : application/x-www-form-urlencoded 형식만 지원하는 상황.
public class HttpBodyParser {
    private HttpBodyParser() {}

    // TODO: HttpBody를 사용할 수 있도록 수정하기
    public static Map<String, String> parse(String stringBody) {
        Map<String, String> datas = new HashMap<>();
        String[] dataArr = stringBody.replaceAll("\n", "").split("&");

        for (String data: dataArr) {
            String[] strings = data.split("=");
            datas.put(strings[0], "");

            if (strings.length == 2) {
                datas.put(strings[0], URLDecoder.decode(strings[1], StandardCharsets.UTF_8));
            }
        }

        return datas;
    }
}
