package controller;

import db.Database;
import enums.ContentTypeEnum;
import enums.ControllerTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.Request;
import response.ResponseFactory;
import webserver.RequestResponseHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class TemplateController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(RequestResponseHandler.class);

    @Override
    public ResponseFactory controllerService(Request request) throws IOException {
        logger.debug("firstLine : " + request.getRequestLine().getURL());
        String url = request.getRequestLine().getURL();
        boolean isDynamic = request.isRequestHaveCookie();
        byte[] body = Files.readAllBytes(new File("./src/main/resources/templates" + url).toPath());
        if(isDynamic){
            body = DynamicController.dynamicIndexHtml(body,request.getRequestHeader().getHeaderValueByKey("Cookie").split("=")[1]);
        }
        if(url.contains("list.html")){
            body = DynamicController.dynamicListHtml(body);
        }
        ResponseFactory responseFactory = new ResponseFactory.Builder()
                .setResponseStatusLine(ControllerTypeEnum.TEMPLATE)
                .setResponseHeader(ContentTypeEnum.HTML, body.length)
                .setResponseBody(body)
                .build();
        return responseFactory;
    }
}
