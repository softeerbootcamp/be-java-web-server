package webserver.domain;

import enums.HttpMethod;
import enums.HttpStatus;

public class RequestLine {
    private HttpMethod httpMethod;
    private String url;
    private String httpVersion;

    public RequestLine(HttpMethod httpMethod, String url, String httpVersion) {
        this.httpMethod = httpMethod;
        this.url = url;
        this.httpVersion = httpVersion;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }


    public String getUrl() {
        return url;
    }


    public String getHttpVersion() {
        return httpVersion;
    }
}
