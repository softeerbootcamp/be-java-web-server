package controller;

import enums.ContentTypeEnum;
import enums.ControllerTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.Request;
import response.Response;
import webserver.RequestResponseHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class StaticController implements Controller{
    private static final Logger logger = LoggerFactory.getLogger(RequestResponseHandler.class);

    @Override
    public void controllerService(Request request, Response response) throws IOException {
        String url =  request.getRequestLine().getURL();
        if(url.contains("/user/css")||url.contains("/user/js")){
            url = url.substring(5);
        }
        logger.debug("firstLine : "+ request.getRequestLine().getURL());
        byte[] body = Files.readAllBytes(new File("./src/main/resources/static"+url).toPath());
        response.responseMaker(ControllerTypeEnum.STATIC, ContentTypeEnum.CSS,body.length,url);
        response.responseBody(body);
    }
}
