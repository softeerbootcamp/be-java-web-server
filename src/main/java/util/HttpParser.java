package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HttpParser {
    private static final String DOMAIN = "http://localhost:8080/";

    public Map<String, String> parseHttpRequest(InputStream in) throws IOException {
        Map<String, String> parsedHttpMap = new HashMap<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String nextLine = br.readLine();
        parsedHttpMap.put("Request Line", nextLine);
        while (!nextLine.equals("")) {
            nextLine = br.readLine();
            String[] line = nextLine.split(": ");
            String key = line[0];
            String value = line[1];
            parsedHttpMap.put(key, value);
        }
        return parsedHttpMap;
    }
}
