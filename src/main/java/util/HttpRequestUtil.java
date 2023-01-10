package util;

import Request.HttpRequest;
import Request.HttpRequestHeaders;
import Request.HttpRequestParams;
import Request.HttpRequestStartLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpRequestUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestUtil.class);
    private static final String COLON = ": ";
    private static final String EQUAL = "=";
    private static final String QUESTION = "\\?";
    private static final String AND = "&";
    private static final String BLANK = " ";
    private static final String BASE_DIR = "./src/main/resources/";

    public static HttpRequest parseRequest(InputStream inputStream) throws IOException {
        Map<String, String> params = new HashMap<>();
        BufferedReader br = null;
        String line = null;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            line = br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new HttpRequest(setParams(line), setHeaders(br), setStartLine(line));
    }
    private static HttpRequestStartLine setStartLine(String line){
        logger.debug("request : "+line);
        String[] temp = line.split(BLANK);
        return new HttpRequestStartLine(temp[0], temp[1], temp[2]);
    }
    private static HttpRequestParams setParams(String path){
        Map<String,String> params = null;
        if(path.startsWith("/user/create")){
            int idx = path.indexOf(QUESTION);
            params = parseQueryString(path.substring(idx+1, path.length()));
        }
        return new HttpRequestParams(params);
    }
    public static Map<String,String> parseQueryString(String query) throws ArrayIndexOutOfBoundsException{
        Map<String, String> querys = new LinkedHashMap<>();
        String[] params = query.split(AND);
        for(String param: params){
            String[] temp = param.split(EQUAL);
            querys.put(URLDecoder.decode(temp[0]), URLDecoder.decode(temp[1]));
            logger.debug("param: "+URLDecoder.decode(temp[0])+' '+URLDecoder.decode(temp[1]));
        }
        return querys;
    }
    private static HttpRequestHeaders setHeaders(BufferedReader br) throws IOException {
        Map<String,String> headers = new HashMap<>();
        String line = br.readLine();
        while(!line.equals("")){
            logger.debug("header: "+ line);
            String[] header = line.split(COLON);
            if(header.length == 2) headers.put(header[0], header[1]);
            line = br.readLine();
        }
        return new HttpRequestHeaders(headers);
    }
}
