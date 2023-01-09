package request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class RequestParserImpl implements RequestParser {
    @Override
    public Map<String, String> parse(InputStream in) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        Map<String, String> requestMap = new HashMap<>();
        try {
            while (bufferedReader.ready()) {
                split(bufferedReader.readLine(), requestMap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return requestMap;
    }

    private void split(String line, Map<String, String> map) {
        int firstIndexOfDelim = line.indexOf(":");
        if(firstIndexOfDelim == -1 && line.length() >= 3) {
            map.put(REQUEST_LINE, line);
        }
        if (firstIndexOfDelim != -1) {
            map.put(line.substring(0, firstIndexOfDelim), line.substring(firstIndexOfDelim + 1).trim());
        }
    }

    public String getMethod(String requestLine) {
        String[] token = requestLine.split(" ");
        return token[0];
    }

    public String getResource(String requestLine) {
        String[] token = requestLine.split(" ");
        return token[1];
    }

    public String getHTTPVersion(String requestLine) {
        String[] token = requestLine.split(" ");
        return token[2];
    }
}
