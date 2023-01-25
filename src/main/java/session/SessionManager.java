package session;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
	private static Map<String, Session> sessions = new HashMap<>();

	public static void addSession(Session session) {
		sessions.put(session.getSessionId(), session);
	}

	public static Session getSession(String sessionId) {
		return sessions.get(sessionId);
	}

	public static boolean valid(String sessionId) {
		return sessions.containsKey(sessionId);
	}

}
