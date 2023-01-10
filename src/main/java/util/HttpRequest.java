package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private Map<String, String> headers = new HashMap<>();
    private String path;
    private String protocol;
    private String method;
    public HttpRequest(InputStream inputStream){
        BufferedReader br = null;
        String line = null;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            line = br.readLine();
            logger.debug("request : "+line);
            String[] temp = line.split(" ");
            method = temp[0];
            path = temp[1];
            protocol = temp[2];

            while(!line.equals("")){
                line = br.readLine();
                logger.debug("header: "+line);
                String[] header = line.split(": ");
                if(header.length == 2) headers.put(header[0], header[1]);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String getPath(){
        return path;
    }
}
