package model.general;

public enum Status {
    OK(200, "OK"),
    BAD_REQUEST(400, "Bad Request");

    private final int statusCode;
    private final String reasonPhrase;

    Status(int statusCode, String reasonPhrase) {
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }
}
