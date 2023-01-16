package model.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestBody {
    private final Map<String, String> content;

    private RequestBody(Map<String, String> content) {
        this.content = content;
    }

    public static RequestBody of(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        int bodyLength = br.read(body, 0, contentLength);

        Map<String, String> content = parseRequestBody(String.valueOf(body));
        return new RequestBody(content);
    }

    public static Map<String, String> parseRequestBody(String body) {
        return Arrays.stream(body.split("&"))
                .map(b -> b.split("="))
                .collect(Collectors.toMap(a -> a[0], a -> a[1]));
    }

    public Map<String, String> getContent() {
        return content;
    }
}
