package http;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Session {

    public static final String DEFAULT_SESSION_ID = "JSESSIONID";

    private final Map<String, String> attributes = new HashMap<>();
    private final UUID id = UUID.randomUUID();

    public String getId() {
        return this.id.toString();
    }

    public void setAttribute(String name, String value) {
        this.attributes.put(name, value);
    }

    public Object getAttribute(String name) {
        return this.attributes.get(name);
    }

}
