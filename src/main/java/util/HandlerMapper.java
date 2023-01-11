package util;

import handler.Handler;
import handler.UserHandler;
import handler.ResourceHandler;
import http.HttpRequest;

public class HandlerMapper {

    public static Handler getHandler(HttpRequest request) {
        String url = request.getUrl();

        if (url.startsWith("/user")) {
            return new UserHandler();
        }

        return new ResourceHandler();
    }
}
