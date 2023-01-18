package session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Session {
	private String sessionId;
	private Map<String, Object> values;

	public Session() {
		this.sessionId = UUID.randomUUID().toString();
		values = new HashMap<>();
	}

	public String getSessionId() {
		return sessionId;
	}

	public Object getAttribute(String key) {
		return values.get(key);
	}

	public void setAttribuite(String key, Object value) {
		values.put(key, value);
	}

}
