package webserver;

import com.github.jknack.handlebars.internal.lang3.StringUtils;

import java.io.*;
import java.util.*;

public class CustomHttpRequest {
    private String protocolVersion;
    private String url;
    private Map<String, List<String>> requestHeaders;
    public CustomHttpRequest(InputStream inputStream){
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(inputStreamReader);
        requestHeaders = new HashMap<>();
        try{
            String[] firstLine = firstLineSpliter(br.readLine());
            this.url = firstLine[1];
            this.protocolVersion = firstLine[2];
            while(br.ready()){
                addToRequestHeader(br.readLine());
            }
            System.out.println("url : " + url);
            System.out.println("protocol : " + protocolVersion);
            System.out.println("request : " + requestHeaders);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getCurrentRootUrl(){
        return null;
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
