package webserver;

public class ControllerHandlerFactory {
    public static ControllerHandler getHandler(HttpRequest httpRequest) {
        String url = httpRequest.getRequestURL();
        if (url.contains("?")) {
            return new QueryStringHandler(httpRequest);
        }
        return new StaticHandler(httpRequest);
    }
}
