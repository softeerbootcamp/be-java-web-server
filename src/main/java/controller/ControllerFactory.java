package controller;

import http.HttpRequest;
import http.HttpResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ControllerFactory {

    private static final List<Controller> controllers;

    private ControllerFactory(){}

    static {
        controllers = new ArrayList<>();
        controllers.add(new IndexController());
        controllers.add(new ResourceController());
        controllers.add(new UserCreateController());
    }

    public static void handle(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        Controller controller = controllers
                .stream()
                .filter(c -> c.isUri(httpRequest))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

        controller.service(httpRequest, httpResponse);
    }


}
