package webserver.domain.response;

import webserver.domain.request.RequestLine;

public class Response {

    private ResponseHeader responseHeader;
    private ResponseBody responseBody;


    public Response(ResponseHeader responseHeader, ResponseBody ResponseBody){
        this.responseHeader = responseHeader;
        this.responseBody = ResponseBody;
    }
    public static Response of(String reqLine, String header, String body) {
        return new Response(ResponseHeader.from(header),ResponseBody.from(body));
    }

    public void readRequest(){
        responseHeader.readHeader();
        responseBody.readBody();
    }
}
