package http;

public enum HttpStatus {
    OK(200, "OK");

    private int statusCode;
    private String status;
    HttpStatus(int statusCode, String status){
        this.statusCode = statusCode;
        this.status = status;
    }
}
