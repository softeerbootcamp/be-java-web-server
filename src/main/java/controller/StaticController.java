package controller;

import enums.ContentTypeEnum;
import enums.ControllerTypeEnum;
import request.Request;
import response.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class StaticController implements Controller{

    @Override
    public void controllerService(Request request, Response response) throws IOException {
        String url =  request.getRequestLine().getURL();
        if(url.contains("/user/css")||url.contains("/user/js")){
            url = url.substring(5);
        }
        System.out.println("firstLine : "+ request.getRequestLine().getURL());
        byte[] body = Files.readAllBytes(new File("./src/main/resources/static"+url).toPath());
        response.responseMaker(ControllerTypeEnum.STATIC, ContentTypeEnum.CSS,body.length,url);
        response.responseBody(body);
    }
}
