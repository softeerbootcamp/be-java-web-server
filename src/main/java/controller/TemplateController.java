package controller;

import db.BoardDatabase;
import enums.ContentTypeEnum;
import enums.ControllerTypeEnum;
import model.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.Request;
import response.ResponseFactory;
import session.HttpSessions;
import webserver.RequestResponseHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.time.LocalDate;

public class TemplateController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(RequestResponseHandler.class);

    @Override
    public ResponseFactory controllerService(Request request) throws IOException, SQLException {
        logger.debug("firstLine : " + request.getRequestLine().getURL());
        String url = request.getRequestLine().getURL();
        ControllerTypeEnum controllerTypeEnum = ControllerTypeEnum.TEMPLATE;
        boolean isLogined = request.isRequestHaveCookie();
        LocalDate now = LocalDate.now();
        logger.debug("body : "+ request.getRequestBody().getBodyLines().toString());

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
        if(isLogined&&url.contains("/qna/form.html")){
            // todo : 생성하는부분 너어어어어어무길다.. sid 다른곳에 저장해둘 것 생각하자.
            Article article = new Article(now.toString()
                    , HttpSessions.findUserIdBySid(request.getRequestHeader().getHeaderValueByKey("Cookie").split("=")[1])
                    ,request.getRequestBody().toString().split("contents=")[1]);
            BoardDatabase.addArticleToBoard(article);
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
