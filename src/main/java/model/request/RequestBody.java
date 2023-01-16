package model.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestBody {
    private final Map<String, String> body;

    private RequestBody(Map<String, String> body) {
        this.body = body;
    }

    public static RequestBody of(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        int bodyLength = br.read(body, 0, contentLength);

        Map<String, String> messageBody = parseRequestBody(String.valueOf(body));
        return new RequestBody(messageBody);
    }

    private static Map<String, String> parseRequestBody(String body) {
        return Arrays.stream(body.split("&"))
                .map(b -> b.split("="))
                .collect(Collectors.toMap(a -> a[0], a -> a[1]));
    }

    public Map<String, String> getBody() {
        return body;
    }
}
