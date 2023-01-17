package controller;

import webserver.*;

public interface Controller {
    default void service(HttpRequest request, HttpResponse response) {

        if(request.getMethod().equals(HttpMethod.GET)) doGet(request,response);
        if(request.getMethod().equals(HttpMethod.POST)) doPost(request,response);
    }

    default void doGet(HttpRequest request,HttpResponse response){
        response.setStatus(HttpStatus.ERROR);
        response.setContentType(ContentType.HTML);
    }

    default void doPost(HttpRequest request,HttpResponse response){
        response.setStatus(HttpStatus.ERROR);
        response.setContentType(ContentType.HTML);
    }

}
