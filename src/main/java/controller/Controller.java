package controller;

import enums.ContentType;
import enums.HttpMethod;
import enums.HttpStatus;
import request.HttpRequest;
import response.HttpResponse;
import view.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;

public interface Controller {
    default String service(HttpRequest request, HttpResponse response, Model model) throws NullPointerException,FileNotFoundException{

        if(request.getMethod().equals(HttpMethod.GET)) return doGet(request,response,model);
        if(request.getMethod().equals(HttpMethod.POST)) return doPost(request,response,model);

        return "";
    }

    default String doGet(HttpRequest request, HttpResponse response, Model model) throws FileNotFoundException {
        File file = new File("./templates/notFound.html");
        try{
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setContentType(ContentType.HTML);
            response.setBody(Files.readAllBytes(file.toPath()));
        }catch(FileNotFoundException e){
            throw e;
        }catch(IOException e){
            e.printStackTrace();
        }

        return "./templates/notFound.html";
    }

    default String doPost(HttpRequest request,HttpResponse response,Model model){
        File file = new File("./templates/notFound.html");
        try{
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setContentType(ContentType.HTML);
            response.setBody(Files.readAllBytes(file.toPath()));
        }catch(Exception e){
            System.out.println("error");
        }

        return "";
    }

}
