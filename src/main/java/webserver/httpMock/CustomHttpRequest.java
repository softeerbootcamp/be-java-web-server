package webserver.httpMock;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CustomHttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private String httpMethod;
    private String protocolVersion;
    private String url;
    private Map<String, String> requestParams;
    private Map<String, List<String>> requestHeaders;
    public CustomHttpRequest(InputStream inputStream){
        requestHeaders = new HashMap<>();
        setFirstLineHeaders(inputStream);
        requestParams = setRequestParams();
    }

    public String getUrl(){
        return url;
    }
    public Map<String, String> getRequestParams(){
        return requestParams;
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
    private Map<String, String> setRequestParams() {
        Map<String, String> requestParams = new HashMap<>();
        if(url.contains("?")){
            String tempParamStr = url.substring(url.indexOf("?")+1);
            String[] params = tempParamStr.split("&");
            for(String param : params){
                try {
                    String[] data = param.split("=");
                    requestParams.put(data[0], data[1]);
                } catch(Exception e){
                    logger.error("String split failed : ", param);
                }
            }
        }
        logger.info("url splited : " + requestParams.toString());
        return requestParams;
    }

    private void setFirstLineHeaders(InputStream inputStream){
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(inputStreamReader);
        try{
            String[] firstLine = firstLineSpliter(br.readLine());
            this.httpMethod = firstLine[0];
            this.url = java.net.URLDecoder.decode(firstLine[1], StandardCharsets.UTF_8);
            this.protocolVersion = firstLine[2];
            while(br.ready()){
                addToRequestHeader(br.readLine());
            }
            logger.info("url : " + this.url  + ", method : " + this.httpMethod + ", headers : " + this.requestHeaders);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String[] firstLineSpliter(String firstLine){
        String[] result = firstLine.split("\\s");
        if(result.length != 3){
            throw new IllegalArgumentException("First Line of Http must have 2 space : " +  firstLine);
        }
        return result;
    }
}
