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
import util.HttpResponseUtils;

import java.io.IOException;

public class DynamicFileController implements Controller{

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Override
    public HttpResponse makeResponse(HttpRequest httpRequest){
        // 로그인 되어있는 유저, ContentType, 파일 확장자, uri를 받음
        User user = Database.findUserById(Session.findUserIdBySessionId(httpRequest.getCookie()));
        String contentType = httpRequest.getContentType();
        String fileNameExtension = httpRequest.getFileNameExtension();
        String uri = httpRequest.getUri();

        if(uri.equals("/index.html")){
            String filePath = HttpResponseUtils.makeFilePath(fileNameExtension);

            // 파일 경로를 넘겨서 http response string 생성
            String responseString = new String(HttpResponseUtils.makeBody(httpRequest.getUri(), filePath));
            byte[] responseBody = responseString.replace("로그인", user.getName()).getBytes();

            // 만들어진 body로 응답 객체를 만들어서 리턴
            return new HttpResponse.HttpResponseBuilder()
                    .setHttpStatusLine(new HttpStatusLine(HttpStatus.OK, httpRequest.getHttpVersion()))
                    .setBody(responseBody)
                    .setContentType(contentType)
                    .makeHeader()
                    .build();
        }

        if(uri.equals("/user/list")){

        }

        return null;
    }
}
