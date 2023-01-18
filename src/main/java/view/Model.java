package view;

import java.util.HashMap;
import java.util.Map;

public class Model {
    private final Map<String, Object> attributes;

    public Model() {
        this.attributes = new HashMap<>();
    }

    public Model addAttribute(String key, Object attribute) {
        this.attributes.put(key, attribute);
        return this;
    }

    public Object getAttribute(String key) {
        return this.attributes.get(key);
    }
}
