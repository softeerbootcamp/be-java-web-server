package http.exception;

public class NullHttpRequestException extends RuntimeException{
    public NullHttpRequestException(String message){
        super(message);
    }
}
