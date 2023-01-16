package request.method.GET.handlers;

public enum GETHandlerEnum {
    FORM("/user/create?", GETUserRegisterHandler.of()),
    NO_MATCH("", GETNoMatchHandler.of())
    ;

    private String url;

    private GETHandler handler;

    private GETHandlerEnum(String url, GETHandler handler) {
        this.url = url;
        this.handler = handler;
    }

    public String getUrl() {
        return this.url;
    }

    public GETHandler getHandler() {
        return this.handler;
    }
}
