package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class HttpRequestUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestUtil.class);
    private static final String COLON = ": ";
    private static final String EQUAL = "=";
    private static final String AND = "&";


    public static Map<String, String> extractParams(String query) {
        Map<String, String> params = new LinkedHashMap<>();
        String[] pieces = query.split(AND);
        for (String param : pieces) {
            String[] temp = param.split(EQUAL);
            try {
                params.put(URLDecoder.decode(temp[0], "UTF-8"), URLDecoder.decode(temp[1], "UTF-8"));
                logger.debug("param: " + URLDecoder.decode(temp[0], "UTF-8") + ' ' + URLDecoder.decode(temp[1], "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        return params;
    }

    public static Map<String, String> extractHeaders(BufferedReader br) throws IOException {
        Map<String, String> headers = new HashMap<>();
        String line = br.readLine();
        while (Objects.nonNull(line) && !line.equals("")) {
            logger.debug("header: " + line);
            String[] header = line.split(COLON);
            if(header[0] == "Cookie"){
                header[1] = extractCookie(header[1]);
            }
            if (header.length == 2) headers.put(header[0], header[1]);
            line = br.readLine();
        }
        return headers;
    }

    private static String extractCookie(String value) {
        String[] split = value.split(EQUAL);
        return split[1];
    }
}
