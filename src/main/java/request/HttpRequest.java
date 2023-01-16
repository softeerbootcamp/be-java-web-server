package request;

import reader.RequestReader;
import util.HttpMethod;
import util.Url;
import util.UrlType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class HttpRequest {
    private final RequestHeader requestHeader;
    private final RequestBody requestBody;
    private final HttpMethod httpMethod;
    private final Url url;


    public HttpRequest(RequestHeader requestHeader, RequestBody requestBody) {
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
        this.httpMethod = HttpMethod.findMethod(requestHeader);
        String urlContext = RequestReader.findPathInRequest(requestHeader);
        this.url = new Url(urlContext, UrlType.getUrlType(urlContext));
    }

    public static HttpRequest getHttpRequest(InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        return new HttpRequest(RequestHeader.makeRequestHeader(bufferedReader), RequestBody.makeRequestBody(bufferedReader));
    }


    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public Url getUrl() {
        return url;
    }

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }
}
