package controller;

import webserver.*;

public class BaseController implements Controller{
    @Override
    public void service(HttpRequest request, HttpResponse response) {

        if(request.getMethod().equals(HttpMethod.GET)) doGet(request,response);
        if(request.getMethod().equals(HttpMethod.POST)) doPost(request,response);
    }

    public void doGet(HttpRequest request,HttpResponse response){
        response.setStatus(HttpStatus.ERROR);
        response.setContentType(ContentType.HTML);
    }

    public void doPost(HttpRequest request,HttpResponse response){
        response.setStatus(HttpStatus.ERROR);
        response.setContentType(ContentType.HTML);
    }
}
