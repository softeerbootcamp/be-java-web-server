package http.request;

import com.google.common.collect.Maps;
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
        if (body.isEmpty()) {
            return Maps.newHashMap();
        }
        return parse(body.toString());
    }

    // todo: handling exception
    private Map<String, String> parseQueryString(String url) {
        if (!url.contains("?")) {
            return Maps.newHashMap();
        }
        String queryString = url.split("\\?", 2)[1];
        return parse(queryString);
    }

    private Map<String, String> parse(String queryString) {
        Map<String, String> parameters = Maps.newHashMap();
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