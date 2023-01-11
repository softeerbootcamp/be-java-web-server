package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpHeader {

    private Map<String, String> headers = new HashMap<>();

    public HttpHeader(List<String> headers){
        for(String header : headers){
            String[] splitedHeader = header.split(":");
            String key = splitedHeader[0].trim();
            String value = splitedHeader[1].trim();
            this.headers.put(key, value);
        }
    }

    public String toString(){
        String headerString = "";
        for(Map.Entry<String, String> header : headers.entrySet()){
            headerString += header.getKey() + ": " + header.getValue() + System.lineSeparator();
        }
        headerString += System.lineSeparator();
        return headerString;
    }

    public String getAccept(){
        return headers.get("Accept");
    }


}
