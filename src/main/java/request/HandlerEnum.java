package request;

import request.handlers.*;

public enum HandlerEnum {
    STATIC(".*\\.(css|js|eot|svg|ttf|woff|woff2|ico)", StaticFileHandler.getInstance()),
    INDEX("^/index", IndexHandler.getInstance()),
    REGISTER("^/user/create", UserCreateHandler.getInstance()),
    LOGIN("^/user/login", LoginHandler.getInstance()),
    LOGOUT("^/user/logout", LogoutHandler.getInstance()),
    LIST("^/user/list", UserListHandler.getInstance());

    private final String urlRegEx;

    private final RequestHandler handler;

    HandlerEnum(String urlRegEx, RequestHandler handler) {
        this.handler = handler;
        if(this.getHandler() != StaticFileHandler.getInstance()) {
            this.urlRegEx = urlRegEx + "(\\.html)?$";
        }
        else {
            this.urlRegEx = urlRegEx;
        }
    }

    public String getUrlRegEx() {
        return urlRegEx;
    }

    public RequestHandler getHandler() {
        return handler;
    }
}
