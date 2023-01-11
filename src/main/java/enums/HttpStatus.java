package enums;

public enum HttpStatus {
    SUCCESS(200, "OK"),
    REDIRECT(302, "FOUND");

    private int code;
    private String reasonPhrase;

    HttpStatus(int code, String reasonPhrase) {
        this.code = code;
        this.reasonPhrase = reasonPhrase;
    }
}
