package httpMock;

import httpMock.constants.ContentType;
import httpMock.constants.StatusCode;

import java.util.HashMap;
import java.util.Map;

public class CustomHttpResponse {

    private StatusCode statusCode;
    private String protocolVersion;
    private final Map<String, String> headers;
    private byte[] body;

    public CustomHttpResponse() {
        this.headers = new HashMap<>();
        body = "".getBytes();
    }

    public void setContentType(ContentType contentType) {
        headers.put("Content-Type", contentType.getContentType()+";charset=UTF-8");
    }

    public void addHeader(String key, String value){
        headers.put(key, value);
    }

    public Map<String,String> getHeaders(){
        return this.headers;
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

    public String toString(){
        return statusCode.getMessage() + " " + headers.toString();
    }

}
