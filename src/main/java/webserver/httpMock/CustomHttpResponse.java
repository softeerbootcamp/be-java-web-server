package webserver.httpMock;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class CustomHttpResponse {

    OutputStream out;
    Integer statusCode;
    List<Byte> body;

    public CustomHttpResponse(OutputStream out){
        this.out = out;
        this.body = new ArrayList<>();
    }

    public OutputStream getOutputStream(){
        return this.out;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Integer getStatusCode(){
        return statusCode;
    }

    public void addToBody(Byte[] datas){
        body.addAll(List.of(datas));
    }

    public List<Byte> getBody(){
        return body;
    }
}
