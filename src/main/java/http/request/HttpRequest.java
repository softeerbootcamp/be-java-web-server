package http.request;

import http.common.HttpHeaders;
import http.common.HttpMethod;
import http.common.URL;

import java.util.Map;

public class HttpRequest {
    private HttpMethod method;
    private URL url;
    private HttpHeaders headers;
    private Map<String, String> data;

    // TODO: 정적 생성자로 처리 가능하나 다른 객체들 또한 함께 변경 필요.
    // TODO: 따라서 나중에 시간 남을 때 진행 예정
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

    public URL getUrl() {
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

    public Map<String, String> getData() {
        return this.data;
    }
}
