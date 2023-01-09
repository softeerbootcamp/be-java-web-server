package webserver.httpMock;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.*;
import java.util.*;

public class CustomHttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private String httpMethod;
    private String protocolVersion;
    private String url;
    private Map<String, List<String>> requestHeaders;
    public CustomHttpRequest(InputStream inputStream){
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(inputStreamReader);
        requestHeaders = new HashMap<>();
        try{
            String[] firstLine = firstLineSpliter(br.readLine());
            this.httpMethod = firstLine[0];
            this.url = firstLine[1];
            this.protocolVersion = firstLine[2];
            while(br.ready()){
                addToRequestHeader(br.readLine());
            }
            logger.info("url : " + this.url  + ", method : " + this.httpMethod + ", headers : " + this.requestHeaders);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getCurrentRootUrl(){
        return url;
    }

    private void addToRequestHeader(String line){
        String[] split1 = line.split(":");
        String itemName  = split1[0].trim();
        if(split1.length < 2){
            return;
        }
        String[] itemString = StringUtils.stripAll(split1[1].split(","));
        List<String> itemList = new ArrayList<>(List.of(itemString));
        this.requestHeaders.put(itemName, itemList);
    }

    private static String[] firstLineSpliter(String firstLine){
        String[] result = firstLine.split("\\s");
        return result;
    }
}
