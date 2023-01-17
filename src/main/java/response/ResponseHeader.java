package response;

import request.Request;

public class ResponseHeader {
    public static String DEFAULT_HTTP_VERSION = "HTTP/1.1";

    private String header;

    private ResponseHeader(byte[] body, HttpResponseStatus httpResponseStatus) {
        header = DEFAULT_HTTP_VERSION + " " + httpResponseStatus.getCode() + "\r\n";
        header += "Content-Type: text/plain;charset=utf-8\r\n";
        header += "Content-Length: " + body.length + "\r\n";
        header += "\r\n";
    }

    private ResponseHeader(byte[] body, String contentType, HttpResponseStatus httpResponseStatus) {
        header = DEFAULT_HTTP_VERSION  + " " +  httpResponseStatus.getCode() + "\r\n";
        header += "Content-Type: " + contentType + ";charset=utf-8\r\n";
        header += "Content-Length: " + body.length + "\r\n";
        header += "\r\n";
    }

    private ResponseHeader(byte[] body, String contentType, HttpResponseStatus httpResponseStatus, String optional) {
        header = DEFAULT_HTTP_VERSION + " " +  httpResponseStatus.getCode() + "\r\n";
        header += "Content-Type: " + contentType + ";charset=utf-8\r\n";
        header += "Content-Length: " + body.length + "\r\n";
        header += optional;
        header += "\r\n";
    }

    public static ResponseHeader of(byte[] body, HttpResponseStatus httpResponseStatus) {
        return new ResponseHeader(body, httpResponseStatus);
    }

    public static ResponseHeader of(byte[] body, String contentType, HttpResponseStatus httpResponseStatus) {
        return new ResponseHeader(body, contentType, httpResponseStatus);
    }

    public static ResponseHeader of(byte[] body, String contentType, HttpResponseStatus httpResponseStatus, String optional) {
        return new ResponseHeader(body, contentType, httpResponseStatus, optional);
    }

    public String getHeader() {
        return header;
    }
}
