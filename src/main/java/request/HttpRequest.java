package request;

import java.util.Map;

import cookie.Cookie;
import model.User;
import session.SessionManager;
import webserver.HttpMethod;
import webserver.Url;

// TODO: method를 enum으로 리팩토링할 예정
public class HttpRequest {

	private HttpRequestLine httpRequestLine;

	private Map<String, String> headers;

	private Map<String, String> body;
	private final User sessionUser;

	private HttpRequest(HttpRequestLine httpRequestLine, Map<String, String> headers, Map<String,String> body) {
		this.httpRequestLine = httpRequestLine;
		this.headers = headers;
		this.body = body;

		if (validSession()) {
			sessionUser = (User)SessionManager.getSession(getCookie().getSessionId()).getAttribute("user");
		} else {
			sessionUser = User.GUEST;
		}
	}

	public User getSessionUser() {
		return sessionUser;
	}

	public HttpMethod getMethod() {
		return httpRequestLine.getHttpMethod();
	}

	public boolean hasCookie() {
		return headers.containsKey("Cookie");
	}

	public Url getUrl() {
		return httpRequestLine.getRequestUrl();
	}

	public static HttpRequest of(HttpRequestLine httpRequestLine, Map<String, String> headers, Map<String, String> body) {
		return new HttpRequest(httpRequestLine, headers, body);
	}

	public Cookie getCookie() {
		if (hasCookie()) {
			return Cookie.from(headers.get("Cookie"));
		} else {
			throw new IllegalArgumentException("쿠키가 존재하지 않습니다.");
		}

	}

	public boolean validSession() {
		return hasCookie() && SessionManager.valid(getCookie().getSessionId());
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
