package http.session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Session {

    public static final String DEFAULT_SESSION_ID = "JSESSIONID";

    private final String id;
    private final Map<String, Object> attributes;

    public Session() {
        this.id = UUID.randomUUID().toString();
        this.attributes = new HashMap<>();
    }

    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    public void invalidate() {
        attributes.clear();
    }

    public String getId() {
        return id;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

}
