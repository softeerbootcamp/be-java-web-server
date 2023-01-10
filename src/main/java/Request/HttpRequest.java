package Request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private HttpRequestParams httpRequestParams;
    private HttpRequestHeaders httpRequestHeaders;
    private HttpRequestStartLine httpRequestStartLine;

    public HttpRequest(HttpRequestParams httpRequestParams, HttpRequestHeaders httpRequestHeaders, HttpRequestStartLine httpRequestStartLine) {
        this.httpRequestParams = httpRequestParams;
        this.httpRequestHeaders = httpRequestHeaders;
        this.httpRequestStartLine = httpRequestStartLine;
    }

    public String getPath(){
        return httpRequestStartLine.getPath();
    }

    public Map<String, String> getParams() {
        return httpRequestParams.getParams();
    }
}
