package servlet;

import request.HttpRequest;
import response.HttpResponse;

public interface Servlet {
    void service(HttpRequest httpRequest);

    void get(HttpRequest httpRequest);

    void post(HttpRequest httpRequest);

    String getResourcePathToRedirect();

//    static final String TEMPLATE_PATH = "./templates";
//
//    void doService(HttpRequest request, HttpResponse response);
}
