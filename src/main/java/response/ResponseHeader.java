package response;

public class ResponseHeader {
    public static String DEFAULT_HTTP_VERSION = "HTTP/1.1";

    public static String CHARSET_UTF_ENCODING = "charset=utf-8";

    private String header;

    private ResponseHeader(byte[] body, HttpResponseStatus httpResponseStatus) {
        header = DEFAULT_HTTP_VERSION + " " + httpResponseStatus.getCode() + "\r\n";
        header += "Content-Type: text/plain;" + CHARSET_UTF_ENCODING + "\r\n";
        header += "Content-Length: " + body.length + "\r\n";
        header += "\r\n";
    }

    private ResponseHeader(byte[] body, String contentType, HttpResponseStatus httpResponseStatus) {
        header = DEFAULT_HTTP_VERSION  + " " +  httpResponseStatus.getCode() + "\r\n";
        header += "Content-Type: " + contentType + ";" + CHARSET_UTF_ENCODING + "\r\n";
        header += "Content-Length: " + body.length + "\r\n";
        header += "\r\n";
    }

    private ResponseHeader(byte[] body, String contentType, HttpResponseStatus httpResponseStatus, String optional) {
        header = DEFAULT_HTTP_VERSION + " " +  httpResponseStatus.getCode() + "\r\n";
        header += "Content-Type: " + contentType + ";" + CHARSET_UTF_ENCODING + "\r\n";
        header += "Content-Length: " + body.length + "\r\n";
        header += optional;
        header += "\r\n";
    }

    public static ResponseHeader of(byte[] body, HttpResponseStatus httpResponseStatus) {
        return new ResponseHeader(body, httpResponseStatus);
    }

    public static ResponseHeader createSimpleRespHeader(byte[] body, String contentType, HttpResponseStatus httpResponseStatus) {
        return new ResponseHeader(body, contentType, httpResponseStatus);
    }

    public static ResponseHeader createFullRespHeader(byte[] body, String contentType, HttpResponseStatus httpResponseStatus, String optional) {
        return new ResponseHeader(body, contentType, httpResponseStatus, optional);
    }

    public String getHeader() {
        return header;
    }
}
