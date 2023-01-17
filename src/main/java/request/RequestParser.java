package request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class RequestParser{
    public static String REQUEST_LINE = "Request-Line";

    public static String parseMethod(String line) {
        return splitRequestLine(line)[0];
    }

    public static String parseResource(String line) {
        return splitRequestLine(line)[1];
    }

    public static String parseVersion(String line) {
        return splitRequestLine(line)[2];
    }

    public static Map<String, String> parseHeader(List<String> requestHeaderList) {
        Map<String, String> requestHeaderMap = new HashMap<>();
        for(String line : requestHeaderList) {
            splitHeader(line, requestHeaderMap);
        }

        return requestHeaderMap;
    }

    private static String[] splitRequestLine(String line) {
        return line.split(" ");
    }

    private static void splitHeader(String line, Map<String, String> map) {
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

    public static Map<String, String> parseBody(Request request) throws IllegalArgumentException{
        Map<String, String> map = new HashMap<>();
        String[] tokens = request.getRequestBody().split("&");
        for(String token : tokens) {
            String[] subTokens = token.split("=");
            if(subTokens.length != 2) {
                throw new IllegalArgumentException();
            }
            map.put(subTokens[0], subTokens[1]);
        }
        return map;
    }
}
