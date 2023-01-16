package util;

import webserver.domain.HttpRequestMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
public class HttpParser {
    private static final String DOMAIN = "http://localhost:8080/";
    public static final String REQUEST_LINE = "Request Line";

    public static HttpRequestMessage parseHttpRequest(InputStream in) throws IOException {
        Map<String, String> header = new HashMap<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String nextLine = br.readLine();
        header.put(REQUEST_LINE, nextLine);
        while (!nextLine.equals("") && nextLine != null) {
            nextLine = br.readLine();
            if (!nextLine.contains(": ")) {
                continue;
            }
            String[] line = nextLine.split(": ");
            String key = line[0];
            String value = line[1];
            header.put(key, value);
        }

        Map<String, String> body = new HashMap<>();
        if (header.get(REQUEST_LINE).contains("POST")) {
            int contentLength = Integer.valueOf(header.get("Content-Length"));
            System.out.println("contentLength = " + contentLength);
            char[] data = new char[contentLength];
            br.read(data, 0, contentLength);
            String line = String.valueOf(data);
            System.out.println(line);
        }
        for (String s : body.keySet()) {
            System.out.println(s +" : " + body.get(s));
        }
        return new HttpRequestMessage(header, body);
    }
    public static Map<String, String> parseQueryString(String query) {
        String[] param = query.split("&");
        Map<String, String> map = new HashMap<>();
        for (String p : param) {
            String[] attributes = p.split("=");
            map.put(attributes[0], attributes[1]);
        }
        return map;
    }
}
