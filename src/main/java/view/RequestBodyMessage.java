package view;

public class RequestBodyMessage {

    private String queryString = "";

    public String getQueryString() {
        return queryString;
    }
    public RequestBodyMessage(char[] body){
        this.queryString = new String(body);
    }
}
