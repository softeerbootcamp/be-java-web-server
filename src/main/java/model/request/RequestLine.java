package model.request;

import model.general.Method;

public class RequestLine {
    private final Method method;
    private final String uri;
    private final String httpVersion;

    private RequestLine(String method, String uri, String httpVersion) {
        this.method = Method.of(method);
        this.uri = uri;
        this.httpVersion = httpVersion;
    }

    public static RequestLine from(String line) {
        String[] lineSplit = line.split(" ");
        String method = lineSplit[0];
        String uri = lineSplit[1];
        String httpVersion = lineSplit[2];

        return new RequestLine(method, uri, httpVersion);
    }

    public String getUri() {
        return uri;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public String getControllerCriteria() {
        return uri.split("/")[1];
    }
}
