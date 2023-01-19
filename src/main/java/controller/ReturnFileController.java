package controller;

import view.Model;
import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.RequestDispatcher;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

public class ReturnFileController implements Controller{

    @Override
    public String doGet(HttpRequest request, HttpResponse response, Model model) throws NullPointerException,IOException, URISyntaxException {
        RequestDispatcher.handle(request,response);

        return "";
    }
}
