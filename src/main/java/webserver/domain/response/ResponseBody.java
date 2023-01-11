package webserver.domain.response;

import java.util.Arrays;
import java.util.HashMap;

import static webserver.utils.CommonUtils.readMap;

public class ResponseBody {


    private HashMap<String, String> bodyMaps;

    public ResponseBody(HashMap<String, String> bodyMaps){
        this.bodyMaps = bodyMaps;
    }
    public static ResponseBody of(HashMap<String, String> bodyMaps){
        return new ResponseBody(bodyMaps);
    }

    public static String[] parseRequestBody(String req){
        return req.split(" ");
    }

    public static ResponseBody from(String req){
        HashMap<String, String> map = new HashMap<>();
        Arrays.stream(req.split("\n")).forEach(item->{
            String[] parsedBody = parseRequestBody(item);
            map.put(parsedBody[0], parsedBody[1]);
        });
        return of(map);
    }

    public void readBody(){
        readMap(bodyMaps);
    }
}
