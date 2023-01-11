package webserver.domain.request;

import webserver.domain.HttpBody;
import webserver.domain.HttpHeader;
import webserver.utils.CommonUtils;

import static webserver.utils.CommonUtils.readMap;

public class Request {

    private HttpHeader requestHeader;
    private HttpBody requestBody;
    private RequestLine requestLine;

    private Request(RequestLine requestLine, HttpHeader requestHeader, HttpBody requestBody){
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }
    public static Request of(String reqLine, String header, String body) {
        return new Request(RequestLine.from(reqLine), HttpHeader.from(header),HttpBody.from(body));
    }

    public void readRequest(){
        requestLine.readReqLine();
        readMap(requestHeader.getHeader());
        readMap(requestBody.getBody());
    }

    public HttpBody getRequestBody(){
        return requestBody;
    }

    public HttpHeader getRequestHeader() {
        return requestHeader;
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

}
