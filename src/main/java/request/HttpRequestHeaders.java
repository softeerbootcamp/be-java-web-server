package request;

import util.HttpRequestUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestHeaders {
    private Map<String, String> headers;

    public HttpRequestHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public static HttpRequestHeaders from(BufferedReader br) {
        Map<String, String> headers = new HashMap<>();

        try {
            headers = HttpRequestUtil.extractHeaders(br);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new HttpRequestHeaders(headers);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public int getContentLength() {
        if(headers.get("Content-Length") == null) {
            return 0;
        }
        return Integer.parseInt(headers.get("Content-Length"));
    }
}
