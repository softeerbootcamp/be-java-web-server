package cookie;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Cookie {
	private Map<String, String> cookies;

	private Cookie(Map<String, String> cookies) {
		this.cookies = cookies;
	}
	// Cookie: name=value; name2=value2
	public static Cookie from(String cookies) {
		String[] cookiesSplit = cookies.split("; ");
		Map<String, String> cookieMap = new HashMap<>();
		cookieMap.putAll(
			Arrays.stream(cookiesSplit).map(p -> p.split("=")).collect(Collectors.toMap(p -> p[0], p -> p[1])));
		return new Cookie(cookieMap);
	}

	public String getSessionId() {
		return cookies.get("SID");
	}
}
