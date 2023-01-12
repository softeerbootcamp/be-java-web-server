package request;

import com.github.jknack.handlebars.internal.lang3.StringUtils;

import webserver.HttpMethod;
import webserver.Url;

public class HttpRequestLine {
	private static final int REQUEST_URL_INDEX = 1;
	private static final int REQUEST_METHOD_INDEX = 0;
	private static final int REQUEST_VERSION_INDEX = 2;

	private HttpMethod httpMethod;

	private Url requestUrl;

	private String version;

	public HttpRequestLine(final HttpMethod httpMethod, final Url requestUrl, final String version) {
		this.httpMethod = httpMethod;
		this.requestUrl = requestUrl;
		this.version = version;
	}

	public Url getRequestUrl() {
		return requestUrl;
	}

	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	public boolean startsWith(String path) {
		return requestUrl.startsWith(path);
	}

	public static HttpRequestLine parse(final String line) {
		final String[] generalStrings = StringUtils.split(line, " ");
		final HttpMethod requestMethod = HttpMethod.from(generalStrings[REQUEST_METHOD_INDEX]);
		final Url requestUrl = Url.of(generalStrings[REQUEST_URL_INDEX]);
		final String version = generalStrings[REQUEST_VERSION_INDEX];
		return new HttpRequestLine(requestMethod, requestUrl, version);
	}

}
