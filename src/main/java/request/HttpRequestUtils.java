package request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jknack.handlebars.internal.antlr.misc.Pair;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

public class HttpRequestUtils {

	public static HttpRequest httpRequestParse(InputStream in) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
			Map<String, String> headers = new HashMap<>();
			Map<String, String> body = new HashMap<>();
			String line = br.readLine();
			HttpRequestLine httpRequestLine = HttpRequestLine.parse(line);
			line = br.readLine();
			while (!line.equals("")) {
				headers.put(line.split(": ")[0], line.split(": ")[1]);
				line = br.readLine();
			}

			if (headers.containsKey("Content-Length")) {
				int contentLength = Integer.parseInt(headers.get("Content-Length"));
				char[] buffer = new char[contentLength];
				br.read(buffer, 0, contentLength);
				String requestBody = new String(buffer);
				String[] tokens = requestBody.split("&");
				body.putAll(
					Arrays.stream(tokens).map(t -> t.split("=")).collect(Collectors.toMap(p -> p[0], p -> p[1])));
			}

			return HttpRequest.of(httpRequestLine, headers, body);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static Map<String, String> parseQueryString(String queryString) {
		if (Strings.isNullOrEmpty(queryString)) {
			return Maps.newHashMap();
		}
		String[] tokens = queryString.split("&");
		return Arrays.stream(tokens)
			.map(t -> t.split("="))
			.collect(Collectors.toMap(p -> p[0], p -> p[1]));

	}

}
