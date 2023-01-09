package webserver.httpMock.constants;

public enum StatusCode {
    OK(200, "OK"),
    NOT_FOUND(404, "NOT_FOUND");
    private int statuscode;
    private String message;

    private StatusCode(int code, String msg){
        statuscode = code;
        message = msg;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public String getMessage(){
        return message;
    }
}
