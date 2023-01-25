package view;
import org.slf4j.LoggerFactory;
import util.MessageParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestReader {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(RequestReader.class);
    private BufferedReader br;
    public RequestReader(BufferedReader br){
        this.br = br;
    }

    public String readStartLine() throws IOException {
        return br.readLine();
    }

    public Map<String, String> readHeader() throws IOException {
        String brStr = "";
        Map<String, String> header = new HashMap<>();
        while(!(brStr=br.readLine()).equals("")){
            MessageParser.parseKeyValue(header,brStr);
            //logger.info(brStr);
        }
        return header;
    }

    public char[] readBody(int size) throws IOException{
        char[] body = new char[size];
        br.read(body);
        //logger.debug(new String(body));
        return body;
    }

}
