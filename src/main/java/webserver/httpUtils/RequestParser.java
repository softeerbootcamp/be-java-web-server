package webserver.httpUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.Paths;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.net.URLDecoder;

public class RequestParser {

    private static final Logger logger = LoggerFactory.getLogger(RequestParser.class);
    private String currentLine;

    public RequestParser(){currentLine = new String();}

    public Request parseRequestFromInputStream(InputStream in) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        currentLine = br.readLine();

        Request req = new Request();
        req.setReqLine(parseRequestLine(br));
        req.setReqHeader(getHeaderKeyValues(br));
        req.setReqBody(getBody(br));

        return req;
    }

    private Map<String, String> parseRequestLine(BufferedReader br) throws IOException
    {
        Map<String, String> parsedRequestLine = new HashMap<String, String>();

        String tokens[] = currentLine.split(" ");
        parsedRequestLine.put(Request.METHOD, tokens[0]);
        parsedRequestLine.put(Request.QUERY, tokens[1].equals("/") ?
                Paths.HOME_PATH :
                URLDecoder.decode(tokens[1]));
        parsedRequestLine.put(Request.VERSION, tokens[2]);
        currentLine = br.readLine();
        return parsedRequestLine;
    }

    private Map<String, String> getHeaderKeyValues(BufferedReader br) throws IOException
    {
        Map<String, String> parsedHeader = new HashMap<String, String>();

        while(!currentLine.isBlank())
        {
            String keyVal[] = currentLine.split(": ");
            parsedHeader.put(keyVal[0], keyVal[1]);

            currentLine = br.readLine();
        }
        return parsedHeader;
    }

    private String getBody(BufferedReader br) throws IOException
    {
        String parsedBody = new String();
        try{
            while(br.ready())
            {
                currentLine = br.readLine();
                if(currentLine == null) break;

                parsedBody.concat(currentLine);
            }
        } catch(Exception e)
        {
            e.printStackTrace();
        }finally {
            System.out.println(parsedBody);
            return parsedBody;
        }
    }
}
