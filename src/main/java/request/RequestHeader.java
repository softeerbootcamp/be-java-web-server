package request;

import java.util.HashMap;
import java.util.Map;

public class RequestHeader {
    private static Map<String,String> headerLineMap;

    public RequestHeader() {
        headerLineMap = new HashMap<>();
    }
    public boolean isHeaderMapContains(String value){
        if(headerLineMap.containsKey(value)) return true;
        return false;
    }
    public void addHeaderMap(String key, String val){
        headerLineMap.put(key,val);
    }
    public String getHeaderValueByKey(String key){
        return headerLineMap.get(key);
    }

}

