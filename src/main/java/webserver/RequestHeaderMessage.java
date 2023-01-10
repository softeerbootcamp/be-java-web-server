package webserver;

import com.google.common.io.Files;
import util.HttpRequestUtil;

public class RequestHeaderMessage {
    private String httpFirstHeader;
    private String httpReqMethod;
    private String httpReqURL;
    private String httpVersion;
    private String httpOnlyURL;
    private String fileExtension;
    public String getHttpOnlyURL() {
        return httpOnlyURL;
    }

    public void setHttpOnlyURL(String httpOnlyURL) {
        this.httpOnlyURL = httpOnlyURL;
    }

    public String getHttpReqParams() {
        return httpReqParams;
    }

    private String httpReqParams;

    public RequestHeaderMessage(String httpFirstHeader){
        this.httpFirstHeader = httpFirstHeader;
        String[] headers = httpFirstHeader.split(" ");
        this.httpReqMethod = headers[0];
        httpReqURL = headers[1].equals("/")?"/index.html":headers[1];
        parseHttpReqURL(httpReqURL);
        httpVersion = headers[2];
    }

    private void parseHttpReqURL(String httpReqURL){
        this.httpOnlyURL = HttpRequestUtil.getOnlyURL(httpReqURL);
        this.httpReqParams = HttpRequestUtil.getURLParams(httpReqURL);
        this.fileExtension = Files.getFileExtension(httpOnlyURL);
    }

    public void isRedirection(){
        if (httpOnlyURL.contains("create")){
            this.httpOnlyURL = "/index.html";
        }
    }

    public String getFileExtension() {
        return fileExtension;
    }
}
