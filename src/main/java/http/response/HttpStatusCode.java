package http.response;

public enum HttpStatusCode {
    OK(200, "OK");

    private final int statusCode;
    private final String message;


    HttpStatusCode(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
