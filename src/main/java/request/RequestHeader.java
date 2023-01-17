package request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestHeader {
    private static List<String> headerLines = new ArrayList<>();
    private static Map<String,String> headerMap;

    public RequestHeader() {
        headerMap = new HashMap<>();
    }
    public boolean isHeaderMapContains(String value){
        if(headerMap.containsKey(value)) return true;
        return false;
    }
    public void addHeaderMap(String key, String val){
        headerMap.put(key,val);
    }
    public String getHeaderValueByKey(String key){
        return headerMap.get(key);
    }
    public void addHeaderLines(String oneLine){
        headerLines.add(oneLine);
    }
    public List<String> getHeaderLines(){
        return headerLines;
    }

}

