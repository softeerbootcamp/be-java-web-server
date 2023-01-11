package webserver;

import java.util.Arrays;

public enum HttpMethod {
	GET("GET"),
	POST("POST"),
	PUT("PUT"),
	PATCH("PATCH"),
	DELETE("DELETE");

	private String method;

	HttpMethod(String method) {
		this.method = method;
	}

	public HttpMethod from(String method) {
		return Arrays.stream(HttpMethod.values())
			.filter(value -> value.method.equals(method))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("존재하는 HttpMethod가 없습니다."));
	}

}
