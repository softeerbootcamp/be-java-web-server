package request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class RequestParser{
    public static String REQUEST_LINE = "Request-line";

    public static Map<String, String> parse(InputStream in) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        Map<String, String> requestMap = new HashMap<>();
        try {
            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine();
                split(line, requestMap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return requestMap;
    }

    private static void split(String line, Map<String, String> map) {
        int firstIndexOfDelim = line.indexOf(":");
        if(firstIndexOfDelim == -1 && line.length() >= 3) {
            map.put(REQUEST_LINE, line);
        }
        if (firstIndexOfDelim != -1) {
            map.put(line.substring(0, firstIndexOfDelim), line.substring(firstIndexOfDelim + 1).trim());
        }
    }

    public static Map<String, String> parseGETQueryString(String resource) {
        Map<String, String> map = new HashMap<>();
        String[] token = resource.split("\\?");
        StringTokenizer stringTokenizer = new StringTokenizer(token[1], "&");
        while(stringTokenizer.hasMoreTokens()) {
            String[] subToken = stringTokenizer.nextToken().split("=");
            map.put(subToken[0], subToken[1]);
        }

        return map;
    }
}
