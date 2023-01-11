package io.request;

import enums.Method;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestFactory {

    private static final List<String> staticExtensions = List.of("css", "fonts", "images", "js", "html");

    public HttpRequest create(InputStream in) throws IOException {
        BufferedReader bufferedReader = getBufferedReader(in);
        String startLine = bufferedReader.readLine();
        String[] chunks = startLine.split(" ");
        Method method = Method.find(chunks[0]);
        String url = chunks[1];
        RequestType requestType = getRequestType(url);
        Map<String, String> queryString = getQueryString(url);

        return new HttpRequest(method, url, requestType, queryString);
    }

    private Map<String, String> getQueryString(String url) {
        if (url.contains("?")) {
            return parseQueryString(url);
        }
        return Map.of();
    }

    private Map<String, String> parseQueryString(String url) {
        Map<String, String> queryString = new HashMap<>();
        String parameter = url.split("\\?", 2)[1];
        String[] split = parameter.split("&");
        for (String s : split) {
            String[] split1 = s.split("=");
            queryString.put(split1[0], split1[1]);
        }
        return queryString;
    }


    private RequestType getRequestType(String url) {
        if (needsForStaticResource(url)) {
            return RequestType.STATIC_RESOURCE_REQUEST;
        }
        return RequestType.OPERATION_REQUEST;
    }

    private boolean needsForStaticResource(String url) {
        return staticExtensions.stream().filter(e -> url.contains(e)).findAny().isPresent();
    }

    private BufferedReader getBufferedReader(InputStream in) {
        return new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
    }
}
