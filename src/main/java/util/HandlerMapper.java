package util;

import handler.Handler;
import handler.UserHandler;
import handler.ResourceHandler;

public class HandlerMapper {

    public static Handler getHandler(String url) {
        if (url.startsWith("/user")) {
            return new UserHandler();
        }

        return new ResourceHandler();
    }
}
