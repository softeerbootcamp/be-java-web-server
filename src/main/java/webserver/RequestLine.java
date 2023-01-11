package webserver;

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

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }
}
