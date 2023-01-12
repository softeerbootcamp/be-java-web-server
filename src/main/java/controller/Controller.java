package controller;

import webserver.HttpRequest;
import webserver.HttpResponse;

public interface Controller {

	public HttpResponse service(HttpRequest httpRequest);
}
