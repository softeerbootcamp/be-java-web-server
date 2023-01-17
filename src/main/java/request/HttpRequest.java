package request;

import java.util.Map;

import webserver.HttpMethod;
import webserver.Url;

// TODO: method를 enum으로 리팩토링할 예정
public class HttpRequest {

	private HttpRequestLine httpRequestLine;

	private Map<String, String> headers;

	private Map<String, String> body;

	private HttpRequest(HttpRequestLine httpRequestLine, Map<String, String> headers, Map<String,String> body) {
		this.httpRequestLine = httpRequestLine;
		this.headers = headers;
		this.body = body;
	}

	public HttpMethod getMethod() {
		return httpRequestLine.getHttpMethod();
	}

	public Url getUrl() {
		return httpRequestLine.getRequestUrl();
	}

	public static HttpRequest of(HttpRequestLine httpRequestLine, Map<String, String> headers, Map<String, String> body) {
		return new HttpRequest(httpRequestLine, headers, body);
	}

	public String getRequestBody(String key) {
		return body.get(key);
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
