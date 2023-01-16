package webserver.handler;

import enums.HttpMethod;
import was.dispatcher.Dispatcher;
import webserver.domain.HttpRequest;

public class ControllerHandlerFactory {
    public static ControllerHandler getHandler(HttpRequest httpRequest) {
        HttpMethod httpMethod =  httpRequest.getRequestLine().getHttpMethod();
        if (httpMethod == HttpMethod.POST) {
            return Dispatcher.getInstance();
        }
        return StaticHandler.getInstance();
    }
}
종