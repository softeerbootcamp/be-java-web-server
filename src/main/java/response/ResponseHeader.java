package response;

import request.Request;

public class ResponseHeader {
    public static String DEFAULT_HTTP_VERSION = "HTTP/1.1";

    private String header;

    private ResponseHeader(HttpResponseStatus httpResponseStatus, byte[] body) {
        header = DEFAULT_HTTP_VERSION + " " + httpResponseStatus.getCode() + "\r\n";
        header += "Content-Type: text/plain;charset=utf-8\r\n";
        header += "Content-Length: " + body.length + "\r\n";
        header += "\r\n";
    }

    private ResponseHeader(HttpResponseStatus httpResponseStatus, Request request, byte[] body) {
        header = DEFAULT_HTTP_VERSION  + " " +  httpResponseStatus.getCode() + "\r\n";
        header += "Content-Type: " + request.getResourceFileContentType() + ";charset=utf-8\r\n";
        header += "Content-Length: " + body.length + "\r\n";
        header += "\r\n";
    }

    private ResponseHeader(HttpResponseStatus httpResponseStatus, Request request, byte[] body, String optional) {
        header = DEFAULT_HTTP_VERSION + " " +  httpResponseStatus.getCode() + "\r\n";
        header += "Content-Type: " + request.getResourceFileContentType() + ";charset=utf-8\r\n";
        header += "Content-Length: " + body.length + "\r\n";
        header += optional;
        header += "\r\n";
    }

    public static ResponseHeader of(HttpResponseStatus httpResponseStatus, byte[] body) {
        return new ResponseHeader(httpResponseStatus, body);
    }

    public static ResponseHeader of(HttpResponseStatus httpResponseStatus, Request request, byte[] body) {
        return new ResponseHeader(httpResponseStatus, request, body);
    }

    public static ResponseHeader of(HttpResponseStatus httpResponseStatus, Request request, byte[] body, String optional) {
        return new ResponseHeader(httpResponseStatus, request, body, optional);
    }

    public String getHeader() {
        return header;
    }
}
