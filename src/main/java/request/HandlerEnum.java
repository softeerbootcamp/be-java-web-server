package request;

import request.handlers.LoginHandler;
import request.handlers.StaticFileHandler;
import request.handlers.TemplateFileHandler;
import request.handlers.UserRegisterHandler;

public enum HandlerEnum {
    STATIC(".*\\.(css|js|eot|svg|ttf|woff|woff2)", StaticFileHandler.getInstance()),
    TEMPLATE(".*\\.(html|ico)$", TemplateFileHandler.getInstance()),
    REGISTER("^/user/create$", UserRegisterHandler.getInstance()),
    LOGIN("^/user/login$", LoginHandler.getInstance())
    ;

    private String urlRegEx;

    private RequestHandler handler;

    private HandlerEnum(String urlRegEx, RequestHandler handler) {
        this.urlRegEx = urlRegEx;
        this.handler = handler;
    }

    public String getUrlRegEx() {
        return urlRegEx;
    }

    public RequestHandler getHandler() {
        return handler;
    }
}
