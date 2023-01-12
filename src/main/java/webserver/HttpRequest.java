package webserver;

import java.util.Map;

// TODO: method를 enum으로 리팩토링할 예정
public class HttpRequest {

	private HttpRequestLine httpRequestLine;

	private Map<String, String> headers;

	private HttpRequest(HttpRequestLine httpRequestLine, Map<String, String> headers) {
		this.httpRequestLine = httpRequestLine;
		this.headers = headers;
	}

	public HttpMethod getMethod() {
		return httpRequestLine.getHttpMethod();
	}

	public Url getUrl() {
		return httpRequestLine.getRequestUrl();
	}

	public static HttpRequest of(HttpRequestLine httpRequestLine, Map<String, String> headers) {
		return new HttpRequest(httpRequestLine, headers);
	}

	public boolean urlStartsWith(String path) {
		return httpRequestLine.startsWith(path);
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	@Override
	public String toString() {
		return "url : " + " method : " + " headers " + headers;
	}
}
