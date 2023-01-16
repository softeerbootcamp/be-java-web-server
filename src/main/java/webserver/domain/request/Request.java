package webserver.domain.request;

import java.util.Map;
import static webserver.utils.CommonUtils.parseStringToMap;

public class Request {

    private Map<String, String> header;
    private RequestLine requestLine;

    private Request(RequestLine requestLine, Map<String, String> header){
        this.requestLine = requestLine;
        this.header = header;
    }
    public static Request of(String reqLine, String header) {
        return new Request(RequestLine.from(reqLine), parseStringToMap(header,"\n"));
    }

    public Map<String, String> getRequestHeader() {
        return header;
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

}
