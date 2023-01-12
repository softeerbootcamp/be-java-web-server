package webserver.httpUtils;

import webserver.Paths;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {


    public static Request parseRequestFromInputStream(InputStream in) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        String currentLine = br.readLine();

        Request req = new Request();
        req.setReqLine(parseRequestLine(currentLine));
    }

    public static Map<String, String> parseRequestLine(String currentLine) throws IOException
    {
        Map<String, String> parsedRequestLine = new HashMap<String, String>();
        String tokens[] = currentLine.split(" ");
        parsedRequestLine.put(Request.REQLINE_METHOD, tokens[0]);
        parsedRequestLine.put(Request.REQLINE_QUERY, tokens[1].equals("/") ? Paths.HOME_PATH : tokens[1]);
        parsedRequestLine.put(Request.REQLINE_VERSION, tokens[2]);
        return parsedRequestLine;
    }
}
