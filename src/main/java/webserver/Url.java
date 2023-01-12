package webserver;

import java.util.HashMap;
import java.util.Map;

public class Url {
	private String path;
	private Map<String, String> queries;

	private Url(String path, Map<String, String> queries) {
		this.path = path;
		this.queries = queries;
	}

	public String getQuery(String key) {
		try {
			return queries.get(key);
		} catch (Exception e) {
			throw new RuntimeException("해당하는 key가 없습니다.");
		}
	}

	public static Url of(String url) {
		int index = url.indexOf('?');
		String queryString = "";
		if (index != -1) {
			queryString = url.substring(index + 1);
			url = url.substring(0, index);
		}

		return new Url(url, HttpRequestUtils.parseQueryString(queryString));
	}

	public boolean startsWith(String path) {
		return this.path.startsWith(path);
	}

	public String getPath() {
		return path;
	}

	@Override
	public String toString() {
		if (queries.size() > 0) {
			path += '?';
			queries.forEach((key, value) -> path += (key + '=' + value));
		}
		return path;
	}

}
