package controller;

import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.RequestDispatcher;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

public class ReturnFileController implements Controller{

    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws NullPointerException,IOException, URISyntaxException {
        RequestDispatcher.handle(request,response);
    }
}
