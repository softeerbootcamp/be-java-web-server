package webserver.domain.request;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static webserver.utils.CommonUtils.readMap;

public class RequestBody {

    private Map<String, String> bodyMaps;

    private RequestBody(Map<String, String> bodyMaps){
        this.bodyMaps = bodyMaps;
    }
    private static RequestBody of(Map<String, String> bodyMaps){
        return new RequestBody(bodyMaps);
    }

    private static String[] parseRequestBody(String req){
        return req.split(" ");
    }

    public static RequestBody from(String req){
        Map<String, String> map = new HashMap<>();
        if(!req.equals("")){
            Arrays.stream(req.split("\n")).forEach(item->{
                String[] parsedBody = parseRequestBody(item);
                map.put(parsedBody[0], parsedBody[1]);
            });
        }
        return of(map);
    }

    public void readBody(){
        readMap(bodyMaps);
    }
}
