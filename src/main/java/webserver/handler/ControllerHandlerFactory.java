package webserver.handler;

import enums.HttpMethod;
import was.dispatcher.PostDispatcher;
import webserver.domain.HttpRequest;

public class ControllerHandlerFactory {
    public static ControllerHandler getHandler(HttpRequest httpRequest) {
        HttpMethod httpMethod =  httpRequest.getRequestLine().getHttpMethod();
        String url = httpRequest.getRequestLine().getUrl();
        if (!url.contains(".")) {
            return PostDispatcher.getInstance();
        }
        return StaticHandler.getInstance();
    }
}