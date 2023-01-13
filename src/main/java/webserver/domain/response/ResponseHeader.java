package webserver.domain.response;

import java.util.Arrays;
import java.util.HashMap;

import static webserver.utils.CommonUtils.readMap;

public class ResponseHeader {

    private HashMap<String, String> headerMaps;

    public ResponseHeader(HashMap<String, String> headerMaps){
        this.headerMaps = headerMaps;
    }
    public static ResponseHeader of(HashMap<String, String> headerMaps){
        return new ResponseHeader(headerMaps);
    }

    public static String[] parseRequestLine(String req){
        return req.split(" ");
    }

    public static ResponseHeader from(String req){
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
