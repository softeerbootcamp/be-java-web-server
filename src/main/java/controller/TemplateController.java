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
        ControllerTypeEnum controllerTypeEnum = ControllerTypeEnum.TEMPLATE;
        boolean isLogined = request.isRequestHaveCookie();
        String addedLine = null;
        byte[] body = Files.readAllBytes(new File("./src/main/resources/templates" + url).toPath());

        if (isLogined) {
            body = DynamicController.dynamicIndexHtml(body, request.getRequestHeader().getHeaderValueByKey("Cookie").split("=")[1]);
        }
        if (url.contains("list.html")) {
            body = DynamicController.dynamicListHtml(body);
        }
        if (!isLogined && url.contains("/qna/form.html")) {
            controllerTypeEnum = ControllerTypeEnum.REDIRECT;
            addedLine = "Location : /index.html";
        }

        ResponseFactory responseFactory = new ResponseFactory.Builder()
                .setResponseStatusLine(controllerTypeEnum)
                .setResponseHeader(ContentTypeEnum.HTML, body.length)
                .addResponseHeader(addedLine)
                .setResponseBody(body)
                .build();
        return responseFactory;
    }
}
