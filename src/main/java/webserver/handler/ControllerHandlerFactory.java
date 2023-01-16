package webserver.handler;

import enums.HttpMethod;
import was.dispatcher.Dispatcher;
import webserver.domain.HttpRequest;

public class ControllerHandlerFactory {
    public static ControllerHandler getHandler(HttpRequest httpRequest) {
        String url = httpRequest.getRequestLine().getUrl();
        HttpMethod httpMethod =  httpRequest.getRequestLine().getHttpMethod();
        if (url.contains("?")) {
            return new QueryStringHandler(httpRequest);
        }
        if (httpMethod == HttpMethod.POST) {
            return new Dispatcher(httpRequest);
        }
        return new StaticHandler(httpRequest);
    }
}
