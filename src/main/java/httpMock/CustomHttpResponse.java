package httpMock;

import httpMock.constants.ContentType;
import httpMock.constants.StatusCode;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CustomHttpResponse {
    public static final byte[] EMPTY_BODY = "".getBytes();
    public static final Map<String, String> EMPTY_HEADER = Collections.EMPTY_MAP;
    private final StatusCode statusCode;
    private final ContentType contentType;
    private Map<String, String> headers;
    public static final Charset CHARSET = StandardCharsets.UTF_8;
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
        return new HashMap<>(this.headers);
    }

    public void addToCookie(String value) {
        if (this.headers == EMPTY_HEADER) {
            this.headers = new HashMap<>();
        }
        String exist = "";
        if (this.headers.containsKey("Set-Cookie")) {
            exist = this.headers.get("Set-Cookie") + ";";
        }
        this.headers.put("Set-Cookie", exist + value);
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

    @Override
    public String toString(){
        return statusCode.getMessage() + " " + headers.toString();
    }

}
