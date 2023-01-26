package service;

import db.SessionDatabase;
import dto.HttpRequest;
import dto.Session;
import dto.SessionCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class SessionService {
    private static final Logger logger = LoggerFactory.getLogger(SessionService.class);

    private static final Map<String, String> options = new HashMap<>();

    public static SessionCookie createSession(String userId, String userName, Map<String, String> options) throws IllegalStateException {
        SessionService.resetOptions();

        SessionService.options.putAll(options);

        SessionCookie s = new SessionCookie(userId, userName, Map.copyOf(SessionService.options));
        SessionDatabase.addSession(s);

        return s;
    }

    public static Session findSessionBySID(String sid) {
        return SessionDatabase.findSessionBySid(sid)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
    }

    private static void resetOptions() {
        options.clear();
        options.put("path", "/");
    }

    public static String extractSidFromCookie(HttpRequest request) {
        Map<String, String> requestHeader = request.getRequestHeader();

        if(requestHeader.containsKey("Cookie")) {
            String sid = requestHeader.get("Cookie");
            sid = Stream.of(sid.split(" "))
                    .map(String::trim)
                    .filter((str) -> str.contains("sid="))
                    .findFirst().orElse("");

            if(sid.charAt(sid.length()-1) == ';') {
                sid = sid.substring(0, sid.length()-1);
            }

            if(!sid.isBlank()) {
                sid = sid.split("=")[1];
                return sid;
            }
        }
        return null;
    }
}
