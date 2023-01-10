package httpMock;

import httpMock.constants.StatusCode;
import httpMock.constants.ContentType;

import java.io.OutputStream;
import java.util.*;

public class CustomHttpResponse {

    private final OutputStream out;
    private StatusCode statusCode;
    private String protocolVersion;
    private final Map<String, String> headers;
    private byte[] body;

    public CustomHttpResponse(OutputStream out) {
        this.out = out;
        this.headers = new HashMap<>();
        body = new byte[0];
    }

    public void setContentType(ContentType contentType) {
        headers.put("Content-Type", contentType.getContentType());
    }

    public void addHeader(String key, String value){
        headers.put(key, value);
    }

    public Map<String,String> getHeaders(){
        return this.headers;
    }
    public OutputStream getOutputStream() {
        return this.out;
    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public void setBody(byte[] datas){
        body = datas;
    }

    public byte[] getBody() {
        return body;
    }

    public void setProtocolVersion(String protocolVersion){
        this.protocolVersion = protocolVersion;
    }

    public String getProtocolVersion(){
        return this.protocolVersion;
    }

}
