package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUri {
    private static final Logger logger = LoggerFactory.getLogger(HttpUri.class);
    private final String uri;

    public HttpUri(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public String getFileNameExtension() {
        return uri.substring(uri.lastIndexOf(".") + 1);
    }
}
