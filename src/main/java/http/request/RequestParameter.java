package http.request;

import http.common.Body;

import java.util.HashMap;
import java.util.Map;

public class RequestParameter {
    private Map<String, String> requestParameters = new HashMap<>();

    public RequestParameter(RequestStartLine startLine, Body body) {
        Map<String, String> queryStringParams = parseQueryString(startLine.getUrl());
        Map<String, String> bodyParams = parseBody(body);
        requestParameters.putAll(queryStringParams);
        requestParameters.putAll(bodyParams);
    }

    // todo: handling exception
    private Map<String, String> parseBody(Body body) {
        byte[] data = body.getData();
        String msg = "";
        for (byte datum : data) {
            msg += (char) datum;
        }
        return parseQueryString(msg);
    }

    // todo: handling exception
    private Map<String, String> parseQueryString(String url) {
        Map<String, String> parameters = new HashMap<>();
        if (!url.contains("?")) {
            return parameters;
        }

        String queryString = url.split("\\?", 2)[1];
        String[] chunks = queryString.split("&");
        for (String chunk : chunks) {
            String[] pair = chunk.split("=");
            parameters.put(pair[0], pair[1]);
        }

        return parameters;
    }

    public String getParameter(String key) {
        return requestParameters.get(key);
    }
}