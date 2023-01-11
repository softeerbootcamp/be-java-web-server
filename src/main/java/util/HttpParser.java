package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
public class HttpParser {
    private static final String DOMAIN = "http://localhost:8080/";
    public static final String REQUEST_LINE = "Request Line";

    public Map<String, String> parseHttpRequest(InputStream in) throws IOException {
        Map<String, String> parsedHttpMap = new HashMap<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String nextLine = br.readLine();
        parsedHttpMap.put(REQUEST_LINE, nextLine);
        while (!nextLine.equals("")) {
            nextLine = br.readLine();
            if (!nextLine.contains(": ")) {
                continue;
            }
            String[] line = nextLine.split(": ");
            String key = line[0];
            String value = line[1];
            parsedHttpMap.put(key, value);
        }
        return parsedHttpMap;
    }
    public Map<String, String> parseQueryString(String query) {
        int index = query.indexOf("?");
        String path = query.substring(0, index);
        String params = query.substring(index + 1);
        String[] param = params.split("&");
        Map<String, String> map = new HashMap<>();
        for (String p : param) {
            String[] attributes = p.split("=");
            map.put(attributes[0], attributes[1]);
        }
        return map;
    }
}
