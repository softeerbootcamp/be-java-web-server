package controller;

import webserver.HttpRequest;
import webserver.HttpResponse;

public interface Controller {

    HttpResponse service(HttpRequest request);

    String getPathUrl();
}
