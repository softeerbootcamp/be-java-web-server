package webserver.httpMock.constants;

public enum StatusCode {
    OK(200, "OK"),
    CREATED(201, "Created"),
    ACCEPTED(202, "Accepted"),
    NO_CONTENT(204, "No Content"),
    RESET_CONTENT(205, "Reset Content"),
    PARTIAL_CONTENT(206, "Partial Content"),
    MULTI_CHOICE(300, "Multiple Choice"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    PAYMENT_REQ(402, "Payment Required"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "NOT_FOUND");
    private int statuscode;
    private String message;

    private StatusCode(int code, String msg){
        statuscode = code;
        message = msg;
    }

    public int getCode() {
        return statuscode;
    }

    public String getMessage(){
        return message;
    }
}
