package controller;

import enums.ContentTypeEnum;
import enums.ControllerTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.Request;
import response.*;
import session.HttpCookie;
import webserver.RequestResponseHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class StaticController implements Controller {
    private String sid;
    private static final Logger logger = LoggerFactory.getLogger(RequestResponseHandler.class);

    @Override
    public ResponseFactory controllerService(Request request) throws IOException {
        String url = request.getRequestLine().getURL();
        boolean isLogined = request.getHttpCookie().isLogin();
        if (!isLogined && url.contains("/qna")) {
            url = url.replace("/qna", "");
        }
        if (url.contains("/user/css") || url.contains("/user/js")) {
            url = url.substring(5);
        }
        logger.debug("firstLine : " + request.getRequestLine().getURL());
        byte[] body = Files.readAllBytes(new File("./src/main/resources/static" + url).toPath());

        ResponseFactory responseFactory = new ResponseFactory.Builder()
                .setResponseStatusLine(ControllerTypeEnum.STATIC)
                .setResponseHeader(ContentTypeEnum.CSS, body.length)
                .setResponseBody(body)
                .build();
        return responseFactory;
    }
}
