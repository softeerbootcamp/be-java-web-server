package http;

public enum HttpStatus {
    OK(200, "OK"),
    FOUND(302,"FOUND");

    private int statusCode;
    private String status;

    HttpStatus(int statusCode, String status) {
        this.statusCode = statusCode;
        this.status = status;
    }

    public String toString() {
        return statusCode + " " + status;
    }
}
