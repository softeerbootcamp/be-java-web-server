package http;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpRequest {

    private static final String ENTER = "\n";
    private static final String COLON = ": ";

    private final HttpStartLine startLine;
    private final Map<String, String> headers;

    private HttpRequest(HttpStartLine startLine, Map<String, String> headers) {
        this.startLine = startLine;
        this.headers = headers;
    }

    public static HttpRequest from(String input) {
        String[] lines = input.split(ENTER);
        String startLine = lines[0];

        return new HttpRequest(HttpStartLine.from(startLine),
                Arrays.stream(Arrays.copyOfRange(lines, 1, lines.length))
                        .map(line -> line.split(COLON))
                        .collect(Collectors.toMap(pair -> pair[0], pair -> pair[1])
                        ));
    }

    public HttpStartLine getStartLine() {
        return startLine;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

}
