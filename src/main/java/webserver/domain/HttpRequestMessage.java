package webserver.domain;

import java.util.Map;

public class HttpRequestMessage {
    private Map<String, String> header;
    private Map<String, String> body;

    public HttpRequestMessage(Map<String, String> header, Map<String, String> body) {
        this.header = header;
        this.body = body;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public Map<String, String> getBody() {
        return body;
    }
}
