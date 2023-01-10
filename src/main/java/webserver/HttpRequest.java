package webserver;

import java.util.Map;

public class HttpRequest {
	private Url url;
	private String method;

	private Map<String, String> headers;

	private HttpRequest(Url url, String method, Map<String, String> headers) {
		this.url = url;
		this.method = method;
		this.headers = headers;
	}

	public Url getUrl() {
		return url;
	}


	public static HttpRequest of(Url url, String method, Map<String, String> headers) {
		return new HttpRequest(url, method, headers);
	}

	public boolean urlStartsWith(String path) {
		return url.startsWith(path);
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	@Override
	public String toString() {
		return "url : " + url + " method : " + method + " headers " + headers;
	}
}
