package request;

import java.io.InputStream;
import java.util.Map;

public interface RequestParser {
    public static final String REQUEST_LINE = "Request-line";
    Map<String, String> parse(InputStream in);

    String getMethod(String requestLine);

    String getResource(String requestLine);

    String getHTTPVersion(String requestLine);
}
