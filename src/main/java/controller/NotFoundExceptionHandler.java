package controller;

import utils.FileIoUtils;
import webserver.ContentType;
import webserver.HttpResponse;
import webserver.HttpStatus;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

public class NotFoundExceptionHandler {

    private static final String TEMPLATES_DIR = "./templates";


    public void writeErrorPage(HttpResponse response){
        showErrorPage(response);

    }

    public static void showErrorPage(HttpResponse res) {
        try{
            byte[] body = FileIoUtils.loadFileFromClasspath("/notFound.html");
            ContentType contentType = ContentType.HTML;
            res.setStatus(HttpStatus.NOT_FOUND);
            res.setContentType(contentType);
            res.addToHeader("Content-Length", String.valueOf(body.length));
            res.setBody(body);//body에는 요청한 파일 내용이 들어감
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

}
