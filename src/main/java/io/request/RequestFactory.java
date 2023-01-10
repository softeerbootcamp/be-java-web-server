package io.request;

import enums.Method;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class RequestFactory {

    private static final List<String> staticExtensions = List.of("css", "fonts", "images", "js", "html");

    public HttpRequest create(InputStream in) throws IOException {
        BufferedReader bufferedReader = getBufferedReader(in);
        String startLine = bufferedReader.readLine();
        String[] chunks = startLine.split(" ");
        Method method = Method.find(chunks[0]);
        String url = chunks[1];
        RequestType requestType = getRequestType(url);
        QueryString queryString = new QueryString(url);

        return new HttpRequest(method, url, requestType, queryString);
    }


    public RequestType getRequestType(String url) {
        if (inStaticResourceList(url)) {
            return RequestType.STATIC_RESOURCE_REQUEST;
        }
        return RequestType.OPERATION_REQUEST;
    }

    private boolean inStaticResourceList(String url) {
        return staticExtensions.stream().filter(e -> url.contains(e)).findAny().isPresent();
    }

    private BufferedReader getBufferedReader(InputStream in) {
        return new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
    }
}
