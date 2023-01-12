package enums;

import java.util.HashMap;
import java.util.Map;

public class QueryString {
    private Map<String, String> queryString;

    public QueryString() {
        this.queryString = new HashMap<>();
    }

    private QueryString(Map<String, String> queryString) {
        this.queryString = queryString;
    }

    public void addAttribute(String key, String value) {
        queryString.put(key, value);
    }

    public String getAttribute(String key) {
        return queryString.get(key);
    }
}
