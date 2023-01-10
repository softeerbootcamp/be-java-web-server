package webserver.domain.request;

public class Request {

    private RequestHeader requestHeader;
    private RequestBody requestBody;
    private RequestLine requestLine;

    public Request(RequestLine requestLine, RequestHeader requestHeader, RequestBody requestBody){
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }
    public static Request of(String reqLine, String header, String body) {
        return new Request(RequestLine.from(reqLine), RequestHeader.from(header),RequestBody.from(body));
    }

    public void readRequest(){
        requestLine.readReqLine();
        requestHeader.readHeader();
        requestBody.readBody();
    }

    public RequestBody getRequestBody(){
        return requestBody;
    }

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

}
