package controller;

import http.HttpRequest;
import http.RequestLine;

import java.util.ArrayList;
import java.util.List;

public class ControllerHandler{

    private static List<Controller> controllers = new ArrayList<>();

    static {
        controllers.add(new UserController());
    }
    public static Controller findController(HttpRequest httpRequest) {
        RequestLine requestLine = httpRequest.getRequestLine();
        if(requestLine.getUri().isEndWithResourceType()) {
            return new ViewController();
        }
        return controllers
                .stream()
                .filter(controller -> controller.isMatch(httpRequest))
                .findFirst()
                .orElseThrow(() -> new ControllerNotFoundException("컨트롤러를 찾을 수 없습니다."));
    }
}
