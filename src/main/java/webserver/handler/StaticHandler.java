package webserver.handler;


import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.domain.HttpResponseMessage;

public class StaticHandler implements ControllerHandler {

    private static StaticHandler staticHandler;

    private StaticHandler() {
    }

    public static StaticHandler getInstance() {
        if (staticHandler != null) {
            return staticHandler;
        }
        return staticHandler = new StaticHandler();
    }
    @Override
    public HttpResponseMessage handle(HttpRequest httpRequest) {
        String uri = httpRequest.getRequestLine().getUrl();
        HttpResponse httpResponse = new HttpResponse();
        return new HttpResponseMessage(httpResponse.forward(httpResponse.findPath(uri)), httpResponse.getBody());
    }
}
