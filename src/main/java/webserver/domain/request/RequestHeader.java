package webserver.domain.request;

import java.util.Arrays;
import java.util.HashMap;

import static webserver.utils.CommonUtils.readMap;

public class RequestHeader {

    private HashMap<String, String> headerMaps;

    private RequestHeader(HashMap<String, String> headerMaps){
        this.headerMaps = headerMaps;
    }
    private static RequestHeader of(HashMap<String, String> headerMaps){
        return new RequestHeader(headerMaps);
    }

    private static String[] parseRequestLine(String req){
        return req.split(" ");
    }

    public static RequestHeader from(String req){
        HashMap<String, String> map = new HashMap<>();
        Arrays.stream(req.split("\n")).forEach(item->{
            String[] parsedHeader = parseRequestLine(item);
            map.put(parsedHeader[0], parsedHeader[1]);
        });
        return of(map);
    }

    public void readHeader(){
        readMap(headerMaps);
    }
}
