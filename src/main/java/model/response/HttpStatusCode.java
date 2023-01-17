package model.response;

public enum HttpStatusCode {

    OK(200, "OK"),
    FOUND(302, "Found"),
    NOT_FOUND(404, "Not Found");

    private final int code;
    private final String msg;

    HttpStatusCode(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return this.code + " " + this.msg + " \r\n";
    }
}
