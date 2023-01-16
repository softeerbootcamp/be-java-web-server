package webserver.domain.request;

import webserver.utils.HttpRequestUtils;

import java.util.Map;
import static webserver.utils.CommonUtils.parseStringToMap;
import static webserver.utils.HttpRequestUtils.parseQueryString;

public class Request {

    private RequestLine requestLine;
    private Map<String, String> header;
    private Map<String, String> body;

    private Request(RequestLine requestLine, Map<String, String> header, Map<String, String> body){
        this.requestLine = requestLine;
        this.header = header;
        this.body= body;
    }
    public static Request of(String reqLine, String header, String body) {
        return new Request(RequestLine.from(reqLine), parseStringToMap(header,"\n"), parseQueryString(body));
    }

    public Map<String, String> getRequestHeader() {
        return header;
    }

    public Map<String, String> getBody() {
        return body;
    }
    public RequestLine getRequestLine() {
        return requestLine;
    }

}
