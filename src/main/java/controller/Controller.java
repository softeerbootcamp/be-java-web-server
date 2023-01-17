package controller;

import webserver.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public interface Controller {
    default void service(HttpRequest request, HttpResponse response) {

        if(request.getMethod().equals(HttpMethod.GET)) doGet(request,response);
        if(request.getMethod().equals(HttpMethod.POST)) doPost(request,response);
    }

    default void doGet(HttpRequest request,HttpResponse response) {
        //NotFoundExceptionHandler.showErrorPage(response);
        File file = new File("./templates/notFound.html");
        try{
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setContentType(ContentType.HTML);
            response.setBody(Files.readAllBytes(file.toPath()));
        }catch(Exception e){
            System.out.println("error");
        }
    }

    default void doPost(HttpRequest request,HttpResponse response){
        File file = new File("./templates/notFound.html");
        try{
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setContentType(ContentType.HTML);
            response.setBody(Files.readAllBytes(file.toPath()));
        }catch(Exception e){
            System.out.println("error");
        }
    }

}
