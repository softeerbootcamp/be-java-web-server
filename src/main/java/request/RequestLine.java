package request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestResponseHandler;
import java.util.HashMap;
import java.util.Map;

public class RequestLine {
    private String requestLine;
    private static final Logger logger = LoggerFactory.getLogger(RequestResponseHandler.class);
    private Map<String, String> requestLineMap;

    public RequestLine() {
        requestLineMap = new HashMap<>();
    }

    public void addRequestLine(String oneLine) {
        logger.debug("set RequestLine : "+oneLine);
        this.requestLine = oneLine;
        //todo : map 사용에 대한 고민이 필요하다.
        String[] lineArr = requestLine.split(" ");
        requestLineMap.put("method",lineArr[0]);
        requestLineMap.put("url",lineArr[1]);
        requestLineMap.put("version",lineArr[2]);
    }

    public String getURL() {
        return requestLineMap.get("url");
    }

    public String getMETHOD() {
        return requestLineMap.get("method");
    }

    public String getVERSION() {
        return requestLineMap.get("version");
    }
}
