package webserver.httpUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {

    public final static String METHOD = "method";

    public final static String QUERY = "query";

    public final static String VERSION = "version";


    public static Map<String, String> parseRequestLine(InputStream in) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        String requestLine = br.readLine();

        Map<String, String> parsedRequestLine = new HashMap<String, String>();
        String tokens[] = requestLine.split(" ");
        parsedRequestLine.put(METHOD, tokens[0]);
        parsedRequestLine.put(QUERY, tokens[1]);
        parsedRequestLine.put(VERSION, tokens[2]);
        return parsedRequestLine;
    }
}
