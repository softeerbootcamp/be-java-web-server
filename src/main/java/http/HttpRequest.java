package http;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpRequest {
    private static final String ROOT = "/";
    private static final String ENTRY_POINT = "/index.html";
    private static final String ENTER = "\n";
    private static final String SPACE = " ";
    private static final String COLON = ": ";

    private final String method;
    private final String path;
    private final String httpVersion;
    private final Map<String, String> headers;

    private HttpRequest(String method, String path, String httpVersion, Map<String, String> headers) {
        this.method = method;
        this.path = path;
        this.httpVersion = httpVersion;
        this.headers = headers;
    }

    public static HttpRequest from(String input) {
        String[] lines = input.split(ENTER);
        String firstLine = lines[0];
        String[] tokens = firstLine.split(SPACE);

        return new HttpRequest(tokens[0], tokens[1], tokens[2],
                Arrays.stream(
                                Arrays.copyOfRange(lines, 1, lines.length))
                        .map(line -> line.split(COLON))
                        .collect(Collectors.toMap(pair -> pair[0], pair -> pair[1])
                        ));
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        if (path.equals(ROOT))
            return ENTRY_POINT;

        return path;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

}
