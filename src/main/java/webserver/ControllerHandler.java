package webserver;

import http.HttpRequest;
import http.RequestLine;

import java.util.ArrayList;
import java.util.List;

public class ControllerHandler{

    private static List<Controller> controllers = new ArrayList<>();

    static {
        controllers.add(new UserController());
        controllers.add(new ViewController());
    }
    public static Controller findController(HttpRequest httpRequest) {
        RequestLine requestLine = httpRequest.getRequestLine();
        if(requestLine.getUri().isEndWithResourceType()) {
            return controllers.get(1);
        }
        return controllers.get(0);
    }
}
