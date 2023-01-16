package webserver.handler;

import enums.HttpMethod;
import was.dispatcher.Dispatcher;
import webserver.domain.HttpRequest;

public class ControllerHandlerFactory {
    public static ControllerHandler getHandler(HttpRequest httpRequest) {
        String url = httpRequest.getRequestLine().getUrl();
        HttpMethod httpMethod =  httpRequest.getRequestLine().getHttpMethod();
        if (url.contains("?")) {
            return QueryStringHandler.getInstance();
        }
        if (httpMethod == HttpMethod.POST) {
            return Dispatcher.getInstance();
        }
        return StaticHandler.getInstance();
    }
}
