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

public class ArticleController implements Controller{
    private static final Logger logger = LoggerFactory.getLogger(RequestResponseHandler.class);

    @Override
    public ResponseFactory controllerService(Request request) throws IOException, SQLException {
        logger.debug("firstLine : " + request.getRequestLine().getURL());
        logger.debug("body : "+ request.getRequestBody().getBodyLines().toString());
        ControllerTypeEnum controllerTypeEnum = ControllerTypeEnum.REDIRECT;
        boolean isLogined = request.getHttpCookie().isLogin();
        LocalDate now = LocalDate.now();
        String addedLine = null;
        byte[] body = Files.readAllBytes(new File("./src/main/resources/templates" + "/index.html").toPath());
        // 로그인이 안됬는데 요청오면 리다이렉트
        if (!isLogined) {
            controllerTypeEnum = ControllerTypeEnum.REDIRECT;
            addedLine = "Location : /index.html";
        }
        //로그인이 됬으면
        if(isLogined){
            String articlebody = request.getRequestBody().getBodyLines().get(0);
            // todo : 생성하는부분 너어어어어어무길다.. sid 다른곳에 저장해둘 것 생각하자.
            Article article = new Article(now.toString()
                    , HttpSessions.findUserIdBySid(request.getRequestHeader().getHeaderValueByKey("Cookie").split("=")[1])
                    ,articlebody.split("contents=")[1]);
            BoardDatabase.addArticleToBoard(article);
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
