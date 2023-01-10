package enums;

public enum LogMessage {
    RESOURCE_NOT_FOUND("resource not found"),

    SERVER_ERROR("server error");
    private String message;

    LogMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
