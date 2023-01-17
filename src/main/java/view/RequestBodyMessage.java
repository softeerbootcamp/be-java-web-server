package view;

public class RequestBodyMessage {


    private String bodyParams = "";

    public String getBodyParams() {
        return bodyParams;
    }
    public RequestBodyMessage(char[] body){
        this.bodyParams = new String(body);
    }
}
