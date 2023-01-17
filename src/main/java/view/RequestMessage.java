package view;

public class RequestMessage {

    private RequestHeaderMessage requestHeaderMessage;
    private RequestBodyMessage requestBodyMessage;

    public RequestHeaderMessage getRequestHeaderMessage() {
        return requestHeaderMessage;
    }

    public RequestBodyMessage getRequestBodyMessage() {
        return requestBodyMessage;
    }

    public RequestMessage(RequestHeaderMessage requestHeaderMessage, RequestBodyMessage requestBodyMessage){
        this.requestHeaderMessage = requestHeaderMessage;
        this.requestBodyMessage = requestBodyMessage;
    }
}
