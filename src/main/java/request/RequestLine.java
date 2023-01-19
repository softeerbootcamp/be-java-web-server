package request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestResponseHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class RequestLine {
    private String requestLine;
    private String METHOD;
    private String URL;
    private String VERSION;
    private boolean userRequest;
    private static final int METHOD_TOKEN_INDEX = 0;
    private static final int URL_TOKEN_INDEX = 1;
    private static final int VERSION_TOKEN_INDEX = 2;
    private static final Logger logger = LoggerFactory.getLogger(RequestResponseHandler.class);
    private static Map<String, String> requestLineMap;

    public RequestLine() {
    }

    public void addRequestLine(String oneLine) {
        logger.debug("set RequestLine : "+oneLine);
        this.requestLine = oneLine;
        //todo : map 사용에 대한 고민이 필요하다.
        String[] lineArr = requestLine.split(" ");
        this.METHOD = lineArr[METHOD_TOKEN_INDEX];
        this.URL = lineArr[URL_TOKEN_INDEX];
        this.VERSION = lineArr[VERSION_TOKEN_INDEX];
    }

    public String getURL() {
        return URL;
    }

    public String getMETHOD() {
        return METHOD;
    }

    public String getVERSION() {
        return VERSION;
    }
}
