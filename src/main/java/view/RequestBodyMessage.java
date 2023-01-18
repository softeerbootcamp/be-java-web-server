package view;

import java.net.URLDecoder;

public class RequestBodyMessage {


    private String bodyParams = "";

    public String getBodyParams() {
        return bodyParams;
    }
    public RequestBodyMessage(char[] body){
        this.bodyParams = URLDecoder.decode(new String(body));
    }
}
