package webserver.domain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static webserver.utils.CommonUtils.readMap;

public class HttpHeader {

    private Map<String, String> headerMaps;

    private HttpHeader(Map<String, String> headerMaps){
        this.headerMaps = headerMaps;
    }

    public void setContent(ContentType contentType, int contentLength){
        headerMaps.put("Content-Type" , contentType.getType());
        headerMaps.put("Content-Length" , String.valueOf(contentLength));
    }

    public void setRedirection(String redirectUrl){
        headerMaps.put("Location" , redirectUrl);
    }

    private static HttpHeader of(Map<String, String> headerMaps){
        return new HttpHeader(headerMaps);
    }

    private static String[] parseRequestLine(String req){
        return req.split(" ");
    }

    public static HttpHeader from(String req){
        Map<String, String> map = new HashMap<>();
        Arrays.stream(req.split("\n")).forEach(item->{
            String[] parsedHeader = parseRequestLine(item);
            map.put(parsedHeader[0], parsedHeader[1]);
        });
        return of(map);
    }

    public Map<String, String> getHeader(){
        return headerMaps;
    }
}
