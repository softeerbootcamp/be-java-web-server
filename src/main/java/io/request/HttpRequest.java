package io.request;

import enums.Method;

import java.util.Map;

public class HttpRequest {

    private Method method;
    private String url;
    private Map<String, String> queryString;
    private RequestType requestType;


    public HttpRequest(Method method, String url, RequestType requestType, Map<String, String> queryString) {
        this.method = method;
        this.url = url;
        this.requestType = requestType;
        this.queryString = queryString;
    }

    public String getUrl() {
        return url;
    }

    public Boolean isStaticResourceRequest() {
        return requestType == RequestType.STATIC_RESOURCE_REQUEST;
    }

    public Map<String, String> getAllQuery() {
        return Map.copyOf(queryString);
    }
}
