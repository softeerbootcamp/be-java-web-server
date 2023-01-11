package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequestLine {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestLine.class);

    private final HttpMethod method;
    private final String url;
    private final String version;

    public HttpRequestLine(String requestLine) {
        String[] splited = requestLine.split(" ");

        this.method = HttpMethod.valueOf(splited[0]);
        this.url = splited[1];
        this.version = splited[2];

        logger.debug("HTTP Method : {}", method);
        logger.debug("URL : {}", url);
        logger.debug("HTTP Version : {}", version);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getVersion() {
        return version;
    }
}
