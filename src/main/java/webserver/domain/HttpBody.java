package webserver.domain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static webserver.utils.CommonUtils.readMap;

public class HttpBody {

    private Map<String, String> bodyMaps;

    private HttpBody(Map<String, String> bodyMaps){
        this.bodyMaps = bodyMaps;
    }
    private static HttpBody of(Map<String, String> bodyMaps){
        return new HttpBody(bodyMaps);
    }

    private static String[] parseRequestBody(String req){
        return req.split(" ");
    }

    public static HttpBody from(String req){
        Map<String, String> map = new HashMap<>();
        if(!req.equals("")){
            Arrays.stream(req.split("\n")).forEach(item->{
                String[] parsedBody = parseRequestBody(item);
                map.put(parsedBody[0], parsedBody[1]);
            });
        }
        return of(map);
    }

    public Map<String, String> getBody(){
        return bodyMaps;
    }
}
