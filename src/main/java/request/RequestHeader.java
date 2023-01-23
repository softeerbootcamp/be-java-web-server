package request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class RequestHeader {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private static final String BLANK = "";
    private static final String lineSeparator = System.lineSeparator();
    private static final String COOKIE = "Cookie";
    private static final String COOKIE_DELIMETER = ";";
    private static final String HEADER_DELIMITER = ":\\s";

    private final Map<String, String> fields;
    private final Set<Map<String, String>> cookies;

    public RequestHeader(Map<String, String> fields, Set<Map<String, String>> cookies) {
        this.fields = fields;
        this.cookies = cookies;
    }

    public static RequestHeader of(BufferedReader bufferedReader) throws IOException {
        Map<String, String> fields = new HashMap<>();
        Set<Map<String, String>> cookies = new HashSet<>();

        while (bufferedReader.ready()) {

            String line = bufferedReader.readLine();
            if (line.equals(BLANK)) break;
            logger.debug("[ Reqeust Header ] line : {}{}",lineSeparator,line);

            String[] headerTokens = line.split(HEADER_DELIMITER);
            if (headerTokens.length >= 2) {
                String key = headerTokens[0].trim();
                String value = headerTokens[1].trim();
                logger.debug("headerTokenKey : {}",key);
                logger.debug("headerTokenValue : {}",value);
                if (key.equals(COOKIE)) {
                    parseCookie(value, cookies);
                    break;
                }
                fields.put(key,value);
                logger.debug("readLastLine:{}",bufferedReader.readLine());
            }
        }

        return new RequestHeader(fields, cookies);
    }

    private static void parseCookie(String value, Set<Map<String, String>> cookies) {
        String[] splitValues = value.split(COOKIE_DELIMETER);

        Map<String, String> cookie = Arrays.stream(splitValues)
                .map(splitValue -> splitValue.split("="))
                .collect(Collectors.toMap(v -> v[0], v -> v[1]));

        cookies.add(cookie);
    }

    public String getCookieValue(String cookieName) {
        return cookies.stream()
                .map(cookie -> cookie.get(cookieName))
                .filter(Objects::nonNull)
                .findAny()
                .orElseGet(() -> "");
    }

    public String toValue() {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, String> field : fields.entrySet()) {
            logger.debug("getKey : {}" , field.getKey());
            logger.debug("getValue : {}" , field.getValue());
            sb.append(String.format("%s: %s%s", field.getKey(), field.getValue(), lineSeparator));
        }

        return sb.toString();
    }

    public Optional<String> getContentLength() {
        return Optional.ofNullable(fields.get("Content-Length"));
    }
}
