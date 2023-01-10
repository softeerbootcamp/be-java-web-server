package webserver;

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


	public static Url of(String path, Map<String, String> queries) {
		return new Url(path, queries);
	}

	public boolean startsWith(String path) {
		return this.path.startsWith(path);
	}

	public String getPath() {
		return path;
	}
	@Override
	public String toString() {
		return "path: " + path + "queries: " + queries;
	}

}
