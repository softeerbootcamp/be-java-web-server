package http.request;

import http.common.HttpHeaders;
import http.common.HttpMethod;
import http.common.URI;

import java.util.Map;

public class HttpRequest {
    private HttpMethod method;
    private URI uri;
    private HttpHeaders headers;
    // TODO: HttpBody를 사용할 수 있도록 수정하기
    private Map<String, String> datas;

    public HttpRequest(
            HttpMethod method,
            URI uri,
            HttpHeaders headers
    )
    {
        this.method = method;
        this.uri = uri;
        this.headers = headers;
    }

    public HttpRequest(
            HttpMethod method,
            URI uri,
            HttpHeaders headers,
            Map<String, String> datas
    )
    {
        this.method = method;
        this.uri = uri;
        this.headers = headers;
        this.datas = datas;
    }

    public HttpMethod getMethod() {
        return this.method;
    }

    public URI getUri() {
        return this.uri;
    }

    public HttpHeaders getHeaders() {
        return this.headers;
    }

    public String getPath() {
        return this.uri.getPath();
    }

    public Map<String, String> getQueries() {
        return this.uri.getQueries();
    }

    public Map<String, String> getDatas() {
        return this.datas;
    }
}
