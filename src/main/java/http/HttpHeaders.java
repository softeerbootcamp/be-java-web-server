package http;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpHeaders {

    private static final String COLON = ": ";

    private final Map<String, String> headers;

    private HttpHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public static HttpHeaders from(String[] lines) {
        return new HttpHeaders(Arrays.stream(Arrays.copyOfRange(lines, 0, lines.length))
                .map(line -> line.split(COLON))
                .collect(Collectors.toMap(pair -> pair[0], pair -> pair[1])
                ));
    }

    public static HttpHeaders createDefaultHeaders() {
        return new HttpHeaders(new HashMap<>());
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public String getValue(String key) {
        return headers.get(key);
    }

    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(headers);
    }

    @Override
    public String toString() {
        return headers.keySet()
                .stream()
                .map(header -> header + ": " + getValue(header) + System.lineSeparator())
                .collect(Collectors.joining());
    }
}
