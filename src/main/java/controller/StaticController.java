package controller;

import enums.ContentTypeEnum;
import enums.ControllerTypeEnum;
import enums.HeaderReferenceEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.Request;
import response.*;
import webserver.RequestResponseHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class StaticController implements Controller{
    private static final Logger logger = LoggerFactory.getLogger(RequestResponseHandler.class);

    @Override
    public NewResponse controllerService(Request request) throws IOException {
        String url =  request.getRequestLine().getURL();
        if(url.contains("/user/css")||url.contains("/user/js")){
            url = url.substring(5);
        }
        logger.debug("firstLine : "+ request.getRequestLine().getURL());
        byte[] body = Files.readAllBytes(new File("./src/main/resources/static"+url).toPath());
        String addedLine="";
        if(request.isRequestHaveCookie()){
            addedLine += HeaderReferenceEnum.SET_COOKIE.getValueWithSpace()+
                    "sid="+
                    request.getRequestHeader().getHeaderValueByKey("Cookie")
            +"; Path=/";
        }

        NewResponse newResponse = new NewResponse.Builder()
                .setResponseStatusLine(ControllerTypeEnum.STATIC)
                .setResponseHeader(ContentTypeEnum.CSS,body.length)
                .addResponseHeader(addedLine)
                .setResponseBody(body)
                .build();
        return newResponse;
        //response.responseMaker(ControllerTypeEnum.STATIC, ContentTypeEnum.CSS,body.length,url);
//        response.responseNewLineAdder();
//        response.responseBody(body);
    }
}
