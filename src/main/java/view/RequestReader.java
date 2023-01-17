package view;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RequestReader {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(RequestReader.class);
    private InputStream in;
    public String startLine;
    public char[] body;
    public RequestReader(InputStream in){
        this.in = in;
    }

    public void readRequest() throws IOException {
        InputStreamReader reader = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(reader);
        startLine = br.readLine();
        String brStr = "";
        body = new char[0];
        while(!(brStr=br.readLine()).equals("")){
            body = readContentLength(brStr,body);
            logger.debug(brStr);
        }
        br.read(body);
        logger.debug(new String(body));
    }

    public char[] readContentLength(String brStr, char[] body){
        if (brStr.contains("Content-Length")){
            int body_sz = Integer.parseInt(brStr.substring("Content-Length: ".length()));
            body = new char[body_sz];
        }
        return body;
    }
}
