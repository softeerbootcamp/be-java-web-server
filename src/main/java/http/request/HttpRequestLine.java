package http.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequestLine {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestLine.class);

    private final HttpMethod method;
    private final String url;
    private final String httpVersion;

    private HttpRequestLine(HttpMethod method, String url, String httpVersion) {
        logger.debug("HTTP Method : {}", method);
        logger.debug("URL : {}", url);
        logger.debug("HTTP Version : {}", httpVersion);

        this.method = method;
        this.url = url;
        this.httpVersion = httpVersion;
    }

    public static HttpRequestLine from(String requestLine) {
        String[] splited = requestLine.split(" ");

        return new HttpRequestLine(HttpMethod.valueOf(splited[0]), splited[1], splited[2]);
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
