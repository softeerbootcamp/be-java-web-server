package webserver;

import java.io.OutputStream;
import java.util.List;

public class CustomHttpResponse {

    OutputStream out;
    Integer statusCode;
    List<Byte> body;

    public CustomHttpResponse(OutputStream out){
        this.out = out;
    }



}
