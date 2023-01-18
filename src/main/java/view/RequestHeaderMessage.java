package view;

import com.google.common.io.Files;
import util.MessageParser;
import util.Session;

import java.util.Map;


public class RequestHeaderMessage {
    private String startLine;
    private String httpReqMethod;
    private String httpReqURL;
    private String httpReqParams;
    private String httpVersion;
    private String httpOnlyURL;
    private String fileExtension;
    private String requestAttribute;
    private String contentType;
    private Map<String, String> header;
    private Map<String, String> cookie;
    private String sessionId;
    public String getHttpOnlyURL() {
        return httpOnlyURL;
    }

    public String getHttpReqParams() {
        return httpReqParams;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public String getRequestAttribute() {return requestAttribute;}

    public String getContentType() {return contentType;}
    public String getSessionId() {return sessionId;}
    public RequestHeaderMessage(String startLine, Map<String,String> header){
        this.startLine = startLine;
        String[] headers = startLine.split(" ");
        this.httpReqMethod = headers[0];
        httpReqURL = headers[1].equals("/")?"/index.html":headers[1];
        parseHttpReqURL(httpReqURL);
        httpVersion = headers[2];
        this.header = header;
        this.cookie = getCookies();
        sessionId = cookie.getOrDefault("sid","");
    }

    private void parseHttpReqURL(String httpReqURL){
        this.httpOnlyURL = MessageParser.getOnlyURL(httpReqURL);
        this.httpReqParams = MessageParser.getURLParams(httpReqURL);
        this.fileExtension = Files.getFileExtension(httpOnlyURL);
        this.requestAttribute = MessageParser.getRequestAttribute(httpOnlyURL);
        this.contentType = "text/" + (fileExtension.equals("js")?"javascript":fileExtension);
    }

    private Map<String,String> getCookies(){
        return MessageParser.parseCookie(header.get("Cookie"));
    }

    public boolean isLogin(){
        return Session.loginSession.get(sessionId) != null;
    }

    public String getSubPath(){
        if (getFileExtension().contains("html"))
            return "/templates";
        return "/static";
    }

}
