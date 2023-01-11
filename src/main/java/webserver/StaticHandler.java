package webserver;

import util.FileFinder;

public class StaticHandler implements ControllerHandler {

    private final HttpRequest httpRequest;

    public StaticHandler(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    @Override
    public HttpResponse handle() {
        String uri = httpRequest.getRequestUri();
        return new HttpResponse(FileFinder.findFile(uri), uri);
    }
}
