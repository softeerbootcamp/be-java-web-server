package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUri {
    private static final Logger logger = LoggerFactory.getLogger(HttpUri.class);
    private String uri;

    public HttpUri(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    // uri에 . 이 있으면 정적 파일을 주면 되지 않을까?
    public boolean isStaticUri() {
        return uri.contains(".");
    }

    public boolean isDynamicUri() {
        return uri.contains("?");
    }
}
