package model.request;

import model.general.ContentType;
import model.general.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestLine {
    private static final Logger logger = LoggerFactory.getLogger(RequestLine.class);

    private final Method method;
    private final String uri;
    private final String httpVersion;

    private RequestLine(String method, String uri, String httpVersion) {
        this.method = Method.from(method);
        this.uri = uri;
        this.httpVersion = httpVersion;
    }

    public static RequestLine from(String line) {
        String[] lineSplit = line.split(" ");

        String method = lineSplit[0];
        String uri = lineSplit[1];
        if(uri.equals("/")) uri = "/index.html";
        String httpVersion = lineSplit[2];

        return new RequestLine(method, uri, httpVersion);
    }

    public Method getMethod() {
        return method;
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

    public Map<String, String> parseQueryString() {
        return Arrays.stream(uri.split("\\?")[1].split("&"))
                .map(s -> s.split("="))
                .collect(Collectors.toMap(a -> a[0], a -> a[1]));
    }

    public ContentType getContentType() {
        String[] uriSplit = getUriWithoutQueryString().split("\\.");

        if(uriSplit.length == 1) return null;

        return ContentType.from("." + uriSplit[uriSplit.length - 1]);
    }

    private String getUriWithoutQueryString() {
        return uri.split("\\?")[0];
    }
}
