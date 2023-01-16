package http.request;

import http.common.HttpHeaders;
import http.common.HttpMethod;
import http.common.URL;

import java.util.Map;

public class HttpRequest {
    private HttpMethod method;
    private URL url;
    private HttpHeaders headers;
    // TODO: HttpBody를 사용할 수 있도록 수정하기
    private Map<String, String> data;

    // TODO: 케이스에 따른 생성자 처리 수정하기
    public HttpRequest(
            HttpMethod method,
            URL url,
            HttpHeaders headers
    )
    {
        this.method = method;
        this.url = url;
        this.headers = headers;
    }

    public HttpRequest(
            HttpMethod method,
            URL url,
            HttpHeaders headers,
            Map<String, String> data
    )
    {
        this.method = method;
        this.url = url;
        this.headers = headers;
        this.data = data;
    }

    public HttpMethod getMethod() {
        return this.method;
    }

    public URL getUri() {
        return this.url;
    }

    public HttpHeaders getHeaders() {
        return this.headers;
    }

    public String getPath() {
        return this.url.getPath();
    }

    public Map<String, String> getQueries() {
        return this.url.getQueries();
    }

    public Map<String, String> getDatas() {
        return this.data;
    }
}
