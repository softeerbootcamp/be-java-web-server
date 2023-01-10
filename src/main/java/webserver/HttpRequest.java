package webserver;

import java.util.Map;

public class HttpRequest {
	private String url;
	private String method;

	private Map<String, String> headers;

	private HttpRequest(String url, String method, Map<String, String> headers) {
		this.url = url;
		this.method = method;
		this.headers = headers;
	}

	public static HttpRequest of(String url, String method, Map<String, String> headers) {
		return new HttpRequest(url, method, headers);
	}

	public String getUrl() {
		return url;
	}

	@Override
	public String toString() {
		return "url : " + url + " method : " + method + " headers " + headers;
	}
}
