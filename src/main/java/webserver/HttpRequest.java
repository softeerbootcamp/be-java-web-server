package webserver;

import java.io.IOException;
import java.util.Map;

import static util.HttpParser.REQUEST_LINE;

public class HttpRequest {

    private final Map<String, String> headers;

    private HttpRequest(Map<String, String> headers) {
        this.headers = headers;
    }

    public static HttpRequest newInstance(Map<String, String> headers) throws IOException {
        return new HttpRequest(headers);
    }

    public String getRequestUri() {
        String[] value = headers.get(REQUEST_LINE).split(" ");
        if (value[1].contains(".html") || value[1].contains(".ico")) {
            return "/templates" + value[1];
        }
        return "/static" + value[1];
    }
    public String getRequestURL() {
        String[] value = headers.get(REQUEST_LINE).split(" ");
        return value[1];
    }
}
