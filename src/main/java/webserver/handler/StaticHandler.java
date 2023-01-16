package webserver.handler;


import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.domain.HttpResponseMessage;

public class StaticHandler implements ControllerHandler {

    private final HttpRequest httpRequest;

    public StaticHandler(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    @Override
    public HttpResponseMessage handle() {
        String uri = httpRequest.getRequestLine().getUrl();
        HttpResponse httpResponse = new HttpResponse();
        return new HttpResponseMessage(httpResponse.forward(httpResponse.findPath(uri)), httpResponse.getBody());
    }
}
