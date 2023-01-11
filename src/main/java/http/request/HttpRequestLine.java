package http.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequestLine {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestLine.class);

    private final HttpMethod method;
    private final String url;
    private final String httpVersion;

    public HttpRequestLine(String requestLine) {
        String[] splited = requestLine.split(" ");

        this.method = HttpMethod.valueOf(splited[0]);
        this.url = splited[1];
        this.httpVersion = splited[2];

        logger.debug("HTTP Method : {}", method);
        logger.debug("URL : {}", url);
        logger.debug("HTTP Version : {}", httpVersion);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getHttpVersion() {
        return httpVersion;
    }
}
