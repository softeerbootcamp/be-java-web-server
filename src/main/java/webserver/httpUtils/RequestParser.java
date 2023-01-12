package webserver.httpUtils;

import webserver.Paths;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestParser {

    private static String currentLine = new String();

    public static Request parseRequestFromInputStream(InputStream in) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        currentLine = br.readLine();

        Request req = new Request();
        req.setReqLine(parseRequestLine(currentLine)); currentLine = br.readLine();
        req.setReqHeader(getHeaderKeyValues(currentLine, br));

        System.out.println(req.getReqLine());
        System.out.println(req.getReqHeader());


        //req.setReqBody(getBody(currentLine, br)); ?? 이거 넣으면 왜 안댐?

        return req;
    }

    private static Map<String, String> parseRequestLine(String currentLine)
    {
        Map<String, String> parsedRequestLine = new HashMap<String, String>();

        String tokens[] = currentLine.split(" ");
        parsedRequestLine.put(Request.REQLINE_METHOD, tokens[0]);
        parsedRequestLine.put(Request.REQLINE_QUERY, tokens[1].equals("/") ? Paths.HOME_PATH : tokens[1]);
        parsedRequestLine.put(Request.REQLINE_VERSION, tokens[2]);

        return parsedRequestLine;
    }

    private static Map<String, String> getHeaderKeyValues(String currentLine, BufferedReader br) throws IOException{
        Map<String, String> ret = new HashMap<String, String>();

        while(!currentLine.isBlank())
        {
            String keyVal[] = currentLine.split(": ");
            ret.put(keyVal[0], keyVal[1]);

            currentLine = br.readLine();
        }
        return ret;
    }

    private static List<String> getBody(String currLine, BufferedReader br) throws IOException {
        List<String> ret = new ArrayList<String>();
        while(!currLine.isBlank())
        {
            ret.add(currLine);
            currLine = br.readLine();
        }
        return ret;
    }
}
