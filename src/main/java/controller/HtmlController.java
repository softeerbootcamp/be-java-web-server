package controller;

import db.Database;
import db.Session;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.HtmlService;
import service.ListService;
import service.StaticFileService;

public class HtmlController implements Controller{
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String htmlFilePath = "./src/main/resources/templates";

    @Override
    public HttpResponse makeResponse(HttpRequest httpRequest){
        //ContentType, 파일 확장자, uri를 받음
        User loginUser = Database.findUserById(Session.findUserIdBySessionId(httpRequest.getCookie()));
        String contentType = httpRequest.getContentType();
        String filePath = htmlFilePath + httpRequest.getUri();
        String httpVersion = httpRequest.getHttpVersion();

        // list service
        if(httpRequest.getUri().equals("/user/list.html"))
            return ListService.service(
                    httpRequest.isLogin(), filePath, httpVersion, contentType);

        // index.html service 로그인 O
        if(httpRequest.isLogin())
            return HtmlService.service(
                    filePath, loginUser, httpVersion, contentType);

        // index.html service 로그인 X
        return StaticFileService.service(
                filePath, httpVersion, contentType);
    }
}
