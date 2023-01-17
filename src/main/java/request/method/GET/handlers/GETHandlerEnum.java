package request.method.GET.handlers;

public enum GETHandlerEnum {
    // TODO: 추후 각 enum 별 url 정규식으로 교체
    FORM("/user/create?", GETUserRegisterHandler.getInstance()),
    NO_MATCH("", GETNoMatchHandler.getInstance())
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
