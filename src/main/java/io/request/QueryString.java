package io.request;

import java.util.HashMap;
import java.util.Map;

public class QueryString {
    private Map<String, String> queryString;

    public QueryString(String url) {
        this.queryString = parse(url);
    }

    private Map<String, String> parse(String url) {
        Map<String, String> queryString = new HashMap<>();
        String s = url.split("\\?", 2)[1];
        String[] split = s.split("&");
        for (String s1 : split) {
            String[] split1 = s1.split("=");
            queryString.put(split1[0], split1[1]);
        }
        return queryString;
    }

    public String get(String key) {
        return queryString.get(key);
    }

    public Map<String, String> getAllQuery() {
        return Map.copyOf(queryString);
    }
}
