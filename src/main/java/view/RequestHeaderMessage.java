package view;

import com.google.common.io.Files;
import util.MessageParser;

public class RequestHeaderMessage {
    private String httpFirstHeader;
    private String httpReqMethod;
    private String httpReqURL;
    private String httpReqParams;
    private String httpVersion;
    private String httpOnlyURL;
    private String fileExtension;
    private String requestAttribute;
    private String contentType;
    private String contentLength;
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

    public RequestHeaderMessage(String httpFirstHeader){
        this.httpFirstHeader = httpFirstHeader;
        String[] headers = httpFirstHeader.split(" ");
        this.httpReqMethod = headers[0];
        httpReqURL = headers[1].equals("/")?"/index.html":headers[1];
        parseHttpReqURL(httpReqURL);
        httpVersion = headers[2];
    }

    private void parseHttpReqURL(String httpReqURL){
        this.httpOnlyURL = MessageParser.getOnlyURL(httpReqURL);
        this.httpReqParams = MessageParser.getURLParams(httpReqURL);
        this.fileExtension = Files.getFileExtension(httpOnlyURL);
        this.requestAttribute = MessageParser.getRequestAttribute(httpOnlyURL);
        this.contentType = "text/" + (fileExtension.equals("js")?"javascript":fileExtension);
    }

}
