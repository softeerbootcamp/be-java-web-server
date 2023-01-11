package webserver.domain.request;

import java.util.Arrays;
import java.util.HashMap;

import static webserver.utils.CommonUtils.readMap;

public class RequestBody {


    private HashMap<String, String> bodyMaps;

    public RequestBody(HashMap<String, String> bodyMaps){
        this.bodyMaps = bodyMaps;
    }
    public static RequestBody of(HashMap<String, String> bodyMaps){
        return new RequestBody(bodyMaps);
    }

    public static String[] parseRequestBody(String req){
        return req.split(" ");
    }

    public static RequestBody from(String req){
        HashMap<String, String> map = new HashMap<>();
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
