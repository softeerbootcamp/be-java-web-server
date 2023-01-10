package webserver;

import util.HttpRequestUtil;

public class RequestHeaderMessage {
    private String httpFirstHeader;
    private String httpReqMethod;
    private String httpReqURL;
    private String httpVersion;
    private String httpOnlyURL;

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
    }
}
