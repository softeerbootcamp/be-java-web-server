package http.request;

public class HttpRequest {
    private final HttpStartLine startLine;
    private final HttpRequestHeader requestHeader;
    private final HttpRequestBody requestBody;

    private HttpRequest(HttpStartLine startLine, HttpRequestHeader requestHeader, HttpRequestBody requestBody) {
        this.startLine = startLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public static HttpRequest of(HttpStartLine startLine, HttpRequestHeader requestHeader, HttpRequestBody requestBody){
        return new HttpRequest(startLine, requestHeader, requestBody);
    }

    public static HttpRequest ofNoBody(HttpStartLine startLine, HttpRequestHeader requestHeader, HttpRequestBody requestBody){
        return new HttpRequest(startLine, requestHeader, requestBody);
    }

    public String getRequestTarget(){
        return this.startLine.getTarget();
    }

    public boolean hasParams() {
        return this.startLine.hasParameter();}

    public String getVersion() {
        return this.startLine.getVersion();
    }

    public String getContentType() {
        return this.startLine.getContentType();
    }
}
