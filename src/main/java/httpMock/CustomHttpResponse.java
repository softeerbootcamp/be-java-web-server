package httpMock;

import com.github.jknack.handlebars.internal.lang3.ArrayUtils;
import httpMock.constants.StatusCode;
import httpMock.constants.ContentType;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomHttpResponse {

    private final OutputStream out;
    private StatusCode statusCode;
    private ContentType contentType;
    private String protocolVersion;
    private byte[] body;

    public CustomHttpResponse(OutputStream out) {
        this.out = out;
        body = new byte[0];
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public ContentType getContentType() {
        return contentType;
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
