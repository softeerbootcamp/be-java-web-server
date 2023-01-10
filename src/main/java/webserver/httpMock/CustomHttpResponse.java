package webserver.httpMock;

import com.github.jknack.handlebars.internal.lang3.ArrayUtils;
import webserver.httpMock.constants.ContentType;
import webserver.httpMock.constants.StatusCode;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class CustomHttpResponse {

    private OutputStream out;
    private StatusCode statusCode;
    private ContentType contentType;
    private List<Byte> body;

    public CustomHttpResponse(OutputStream out){
        this.out = out;
        this.body = new ArrayList<>();
    }

    public void setContentType(ContentType contentType){
        this.contentType = contentType;
    }

    public ContentType getContentType(){
        return contentType;
    }
    public OutputStream getOutputStream(){
        return this.out;
    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public StatusCode getStatusCode(){
        return statusCode;
    }

    public void addToBody(Byte[] datas){
        body.addAll(List.of(datas));
    }

    public void addToBody(byte[] datas){
        addToBody(ArrayUtils.toObject(datas));
    }

    public List<Byte> getBody(){
        return body;
    }
}
