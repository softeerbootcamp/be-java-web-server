package util;

import model.Board;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.domain.HttpRequestMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class HttpParser {
    public static final String REQUEST_LINE = "Request Line";
    private static final Logger logger = LoggerFactory.getLogger(HttpParser.class);

    public static HttpRequestMessage parseHttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        Map<String, String> header = parseHeader(br);
        Map<String, String> body = parseBody(header, br);

        return new HttpRequestMessage(header, body);
    }

    private static Map<String, String> parseHeader(BufferedReader br) throws IOException {
        Map<String, String> header = new HashMap<>();
        String nextLine = br.readLine();
        header.put(REQUEST_LINE, nextLine);
        logger.debug("{}: {}", REQUEST_LINE, nextLine);
        while (nextLine != null && !nextLine.equals("")) {
            nextLine = br.readLine();
            if (!nextLine.contains(": ")) {
                continue;
            }
            logger.debug("{}", nextLine);
            String[] line = nextLine.split(": ");
            String key = line[0];
            String value = line[1];
            header.put(key, value);
        }
        return header;
    }

    private static Map<String, String> parseBody(Map<String, String> header, BufferedReader br) throws IOException {
        Map<String, String> body = new HashMap<>();
        if (header.get(REQUEST_LINE).contains("POST")) {
            int contentLength = Integer.valueOf(header.get("Content-Length"));
            char[] data = new char[contentLength];
            br.read(data, 0, contentLength);
            String line = String.valueOf(data);
            body = parseQueryString(URLDecoder.decode(line, "UTF-8"));
            for (String s : body.keySet()) {
                logger.debug("{}: {}", s, body.get(s));
            }
        }
        return body;
    }

    public static Map<String, String> parseQueryString(String query) {
        String[] param = query.split("&");
        Map<String, String> map = new HashMap<>();
        for (String p : param) {
            String[] attributes = p.split("=");
            if (attributes.length != 2) {
                throw new NullPointerException();
            }
            map.put(attributes[0], attributes[1]);
        }
        return map;
    }

    public static String parseSessionId(String query) {
        return query.substring(query.indexOf("=") + 1);
    }

    public static Board parseToBoard(Map<String, String> httpBody) {
        return new Board(httpBody.get("writer"), httpBody.get("content"));
    }
}
