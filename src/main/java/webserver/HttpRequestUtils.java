package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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

	private static final Logger logger = LoggerFactory.getLogger(HttpRequestUtils.class);

	public static HttpRequest httpRequestParse(InputStream in) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			String line = br.readLine();

			// 메서드 설정
			String method = line.substring(0, line.indexOf(' '));
			line = line.substring(line.indexOf(' ') + 1);
			line = line.split(" ")[0];
			// url 설정
			String path = line;
			int index = line.indexOf('?');
			String queryString = "";
			if (index != -1) {
				path = line.substring(0, index);
				queryString = line.substring(index + 1);
			}
			Url url = Url.of(path, parseQueryString(queryString));

			line = br.readLine();
			Map<String, String> headers = new HashMap<>();
			while (!line.equals("")) {
				headers.put(line.split(": ")[0], line.split(": ")[1]);
				line = br.readLine();

			}

			return HttpRequest.of(url, method, headers);
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

	public static Pair<String, String> getKeyValue(String keyValue, String regex) {
		if (Strings.isNullOrEmpty(keyValue)) {
			return null;
		}

		String[] tokens = keyValue.split(regex);
		if (tokens.length != 2) {
			return null;
		}
		return new Pair<String, String>(tokens[0], tokens[1]);
	}

	public String pathParse(String line) {
		String[] str = line.split(" ");
		return str[1];
	}
}
