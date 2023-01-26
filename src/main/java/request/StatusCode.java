package request;

public enum StatusCode {
    CONTINUE(100, "Continue"),
    OK(200, "OK"),
    CREATED(201, "Created"),
    ACCEPTED(202, "Accepted"),
    NO_CONTENT(204, "No Content"),
    FOUND(302, "Found"),
    SEE_OTHER(303, "See Other"),
    TEMPORARY_REDIRECT(307, "Temporary Redirect"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    PAYMENT_REQUIRED(402, "Payment Required"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found");

    private int code;
    private String statusText;

    StatusCode(int code, String statusText) {
        this.code = code;
        this.statusText = statusText;
    }

    @Override
    public String toString() {
        return code + " " + statusText;
    }
}
