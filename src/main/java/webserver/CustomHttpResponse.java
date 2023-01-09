import java.util.List;

public class CustomHttpResponse {

    Integer statusCode;
    List<Byte> body;

    public CustomHttpResponse(int statusCode){
        this.statusCode = statusCode;
    }



}
