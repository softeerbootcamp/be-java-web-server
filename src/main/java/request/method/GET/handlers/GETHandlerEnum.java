package request.method.GET.handlers;

import request.Request;
import response.HttpResponseStatus;
import response.Response;

public enum GETHandlerEnum {
    FORM("^/user/create\\?.*", GETUserRegisterHandler.getInstance()),
    NO_MATCH("", GETNoMatchHandler.getInstance())
    ;

    private String urlRegEx;

    private GETHandler handler;

    private GETHandlerEnum(String urlRegEx, GETHandler handler) {
        this.urlRegEx = urlRegEx;
        this.handler = handler;
    }

    public String getUrlRegEx() {
        return this.urlRegEx;
    }

    public GETHandler getHandler() {
        return this.handler;
    }
}
