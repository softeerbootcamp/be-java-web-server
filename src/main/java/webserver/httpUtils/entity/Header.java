package webserver.httpUtils.entity;

import java.util.HashMap;
import java.util.Map;

public class Header {
    private Map<String, String> context = new HashMap<>();

    public void insertKeyVal(String key, String val) {
        context.put(key, val);
    }

    public Map<String, String> getAll() {
        return context;
    }
}
