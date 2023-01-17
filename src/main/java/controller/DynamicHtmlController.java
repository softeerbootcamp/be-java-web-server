package controller;

import db.Database;
import db.Session;
import http.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpStatusLine;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.IndexHtmlService;
import service.ListService;
import util.HttpResponseUtils;

import java.util.Collection;

public class DynamicHtmlController implements Controller{
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Override
    public HttpResponse makeResponse(HttpRequest httpRequest){
        //ContentType, 파일 확장자, uri를 받음
        User loginUser = Database.findUserById(Session.findUserIdBySessionId(httpRequest.getCookie()));
        String contentType = httpRequest.getContentType();
        String fileNameExtension = httpRequest.getFileNameExtension();
        String uri = httpRequest.getUri();
        String httpVersion = httpRequest.getHttpVersion();

        if(uri.equals("/user/list.html")){
            return ListService.service(httpRequest.isLogin(), fileNameExtension, uri, httpVersion, contentType);
        }

        // /index.html 로그인 O
        return IndexHtmlService.service(fileNameExtension, uri, loginUser, httpVersion, contentType);
    }
}
