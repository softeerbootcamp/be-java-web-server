package request.method.GET.handlers;

public enum HandlerEnum {
    FORM("/user/create?", UserRegisterHandler.of()),
    NO_MATCH("", NoMatchHandler.of())
    ;

    private String url;

    private GETHandler handler;

    private HandlerEnum(String url, GETHandler handler) {
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
