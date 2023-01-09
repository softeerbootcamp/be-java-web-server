package request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class RequestParser{
    public static String REQUEST_LINE = "Request-line";
    private static Map<String, String> parse(InputStream in) {
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
        if(firstIndexOfDelim == -1 && line.length() >= 6) {
            map.put(REQUEST_LINE, line);
        }
        if (firstIndexOfDelim != -1) {
            map.put(line.substring(0, firstIndexOfDelim), line.substring(firstIndexOfDelim + 1).trim());
        }
    }

    public static String getMethod(InputStream in) {
        String[] token = parse(in).get(REQUEST_LINE).split(" ");
        return token[0];
    }

    public static String getResource(InputStream in) {
        String[] token = parse(in).get(REQUEST_LINE).split(" ");
        return token[1];
    }

    public static String getHTTPVersion(InputStream in) {
        String[] token = parse(in).get(REQUEST_LINE).split(" ");
        return token[2];
    }

    public static Map<String, String> getAllHeaders(InputStream in) {
        return parse(in);
    }
}
