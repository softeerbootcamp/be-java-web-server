package model.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestBody {
    private static final Logger logger = LoggerFactory.getLogger(RequestBody.class);

    private final Map<String, String> content;

    private RequestBody(Map<String, String> content) {
        this.content = content;
    }

    public static RequestBody of(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        int bodyLength = br.read(body, 0, contentLength);

        logger.debug("Request Body: {}", String.valueOf(body));
        Map<String, String> content = parseRequestBody(URLDecoder.decode(String.valueOf(body), StandardCharsets.UTF_8));
        return new RequestBody(content);
    }

    public static Map<String, String> parseRequestBody(String body) {
        if (body == null || body.isEmpty()) {
            throw new IllegalArgumentException("Request body cannot be null or empty");
        }

        try {
            return Arrays.stream(body.split("&"))
                    .map(b -> b.split("="))
                    .collect(Collectors.toMap(a -> a[0], a -> a[1]));
        } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid format for request body: " + body);
        }
    }

    public Map<String, String> getContent() {
        return content;
    }
}
