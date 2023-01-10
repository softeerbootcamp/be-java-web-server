package http;

public class HttpRequest {
    private final HttpStartLine startLine;
    private final HttpRequestHeader requestHeader;
    private final HttpRequestBody requestBody;

    public HttpRequest(HttpStartLine startLine, HttpRequestHeader requestHeader, HttpRequestBody requestBody) {
        this.startLine = startLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public String getRequestTarget(){
        return this.startLine.getTarget();
    }
}
