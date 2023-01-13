package httpMock;

import httpMock.constants.ContentType;
import httpMock.constants.StatusCode;

import java.util.Collections;
import java.util.Map;

public class CustomHttpResponse {
    public static final byte[] EMPTY_BODY = "".getBytes();
    public static final Map<String, String> EMPTY_HEADER = Collections.EMPTY_MAP;
    private final StatusCode statusCode;
    private final ContentType contentType;
    private final Map<String, String> headers;
    private final byte[] body;

    public CustomHttpResponse(StatusCode statusCode, ContentType contentType, Map<String, String> headers, byte[] body) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.body = body;
        this.contentType = contentType;
    }

    public static CustomHttpResponse of(StatusCode statusCode, ContentType contentType, Map<String, String> headers, byte[] body) {
        return new CustomHttpResponse(statusCode, contentType, headers, body);
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public String getContentTypeLine(){
        return "Content-Type: " + contentType.getContentType();
    }

    public String getStatusLine(String protocol){
        return protocol + " " + statusCode.getCode() + " " + statusCode.getMessage();
    }

    public byte[] getBody() {
        return body;
    }

    public String toString(){
        return statusCode.getMessage() + " " + headers.toString();
    }

}
