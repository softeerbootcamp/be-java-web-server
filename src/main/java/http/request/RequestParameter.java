package http.request;

import http.common.Body;

import java.util.HashMap;
import java.util.Map;

public class RequestParameter {
    private Map<String, String> requestParameters;

    public RequestParameter(Body body) {
        this.requestParameters = parseBody(body);
    }

    private Map<String, String> parseBody(Body body) {
        byte[] data = body.getData();
        String msg = "";
        for (byte datum : data) {
            msg += (char) datum;
        }
        return parseQueryString(msg);
    }

    public RequestParameter(RequestStartLine startLine) {
        this.requestParameters = parseQueryString(startLine.getUrl());
    }


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