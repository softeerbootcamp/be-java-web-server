package webserver.httpUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.constants.Paths;
import webserver.httpUtils.entity.ReqLine;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.net.URLDecoder;

public class RequestGetter {

    private static final Logger logger = LoggerFactory.getLogger(RequestGetter.class);

    public RequestGetter(){ }

    public Request getFromInputStream(InputStream in) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line = br.readLine();

        Request req = new Request();
        try{

            ReqLine parsedLine = parseRequestLine(line);

            req.setReqLine(parsedLine.getMethod(), parsedLine.getQuery(), parsedLine.getVersion());
            req.setReqHeader(getHeaderKeyValues(br));
            req.setReqBody(getBody(br));
        } catch (NullPointerException e)
        {
            logger.error("Request가 널임");
        }

        return req;
    }

    private ReqLine parseRequestLine(String line) throws IOException
    {
        ReqLine parsedRequestLine = new ReqLine();

        String tokens[] = line.split(" ");
        parsedRequestLine.setMethod(tokens[0]);
        parsedRequestLine.setQuery(tokens[1].equals("/") ?
                Paths.HOME_PATH :
                URLDecoder.decode(tokens[1]));
        parsedRequestLine.setVersion(tokens[2]);

        return parsedRequestLine;
    }

    private Map<String, String> getHeaderKeyValues(BufferedReader br) throws IOException
    {
        Map<String, String> parsedHeader = new HashMap<String, String>();
        String line = br.readLine();

        while(!line.isBlank())
        {
            try{

                String keyVal[] = new String[2];
                keyVal = line.split(": ");
                parsedHeader.put(keyVal[0], keyVal[1]);
            } catch(ArrayIndexOutOfBoundsException e)
            {
                logger.error(line);
            }
            line = br.readLine();
        }
        return parsedHeader;
    }

    private String getBody(BufferedReader br) throws IOException
    {
        String parsedBody = new String();
        try{
            StringBuffer sb = new StringBuffer();
            while(br.ready())
            {
                sb.append(((char) br.read()));
            }
            parsedBody = sb.toString();
        } catch(Exception e)
        {
            e.printStackTrace();
        }finally {
            return URLDecoder.decode(parsedBody);
        }
    }
}
