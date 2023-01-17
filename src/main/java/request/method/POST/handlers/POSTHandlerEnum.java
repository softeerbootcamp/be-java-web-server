package request.method.POST.handlers;


public enum POSTHandlerEnum {
    FORM("/user/create", POSTUserRegisterHandler.getInstance()),
    LOGIN("/user/login", POSTLoginHandler.getInstance()),
    NO_MATCH("", POSTNoMatchHandler.getInstance())
    ;

    private String url;

    private POSTHandler postHandler;

    private POSTHandlerEnum(String url, POSTHandler postHandler) {
        this.url = url;
        this.postHandler = postHandler;
    }

    public String getUrl() {
        return this.url;
    }

    public POSTHandler getPostHandler() {
        return this.postHandler;
    }
}
