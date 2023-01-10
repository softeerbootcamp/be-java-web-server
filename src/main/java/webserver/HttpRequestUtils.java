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
			String url = line.split(" ")[1];
			String method = line.split(" ")[0];
			line = br.readLine();
			Map<String, String> headers = new HashMap<>();

			while(!line.equals("")) {
				headers.put(line.split(": ")[0], line.split(": ")[1]);
				line = br.readLine();
				logger.info(line);

			}
			return HttpRequest.of(url, method, headers);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
	// public static Map<String, String> parseQueryString(String queryString) {
	// 	if (Strings.isNullOrEmpty(queryString)) {
	// 		return Maps.newHashMap();
	// 	}
	//
	// 	String[] tokens = queryString.split("&");
	// 	return Arrays.stream(tokens)
	// 		.map(t -> getKeyValue(t, "="))
	// 		.filter(p -> p != null)
	// 		.collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
	// }

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
