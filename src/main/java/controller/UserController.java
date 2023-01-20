package controller;


import controller.annotation.Auth;
import controller.annotation.ControllerInfo;
import controller.annotation.ControllerMethodInfo;
import db.Database;
import db.UserDatabase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reader.fileReader.FileReader;
import reader.fileReader.TemplatesFileReader;
import reader.requestReader.RequestPostReader;
import reader.requestReader.RequestReader;
import request.HttpRequest;
import request.RequestDataType;
import request.Url;
import response.Data;
import response.HttpResponse;
import service.SessionService;
import service.UserService;
import util.*;
import util.error.erroclass.FailLoggedException;
import view.UserRender;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

@ControllerInfo
public class UserController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private Database userDatabase = new UserDatabase();
    private UserService userService = new UserService();

    private SessionService sessionService = new SessionService();

    private RequestReader requestReader;
    private FileReader fileReader;

    private UserRender userRender = UserRender.getInstance();


    @ControllerMethodInfo(path = "/user/create", method = HttpMethod.POST)
    public HttpResponse UserQueryString(DataOutputStream dataOutputStream, HttpRequest httpRequest) throws IOException {
        requestReader = new RequestPostReader();

        Map<String, String> userMap = requestReader.readData(httpRequest);
        User user = userService.createModel(userMap);
        userDatabase.addData(user);

        Cookie cookie = sessionService.persistUser(user);

        logger.debug("저장된 user:{}", userDatabase.findObjectById(user.getUserId()));
        logger.debug("생성된 Cookie-sid:{}", cookie.getValue());

        return new HttpResponse.Builder()
                .setData(new Data(dataOutputStream))
                .setFileType(FileType.HTML)
                .setHttpStatus(HttpStatus.RE_DIRECT)
                .setRedirectUrl(new Url("/index.html",RequestDataType.FILE))
                .setCookie(cookie)
                .build();
    }

    @ControllerMethodInfo(path = "/user/login", method = HttpMethod.POST)
    public HttpResponse login(DataOutputStream dataOutputStream, HttpRequest httpRequest) throws IOException, FailLoggedException {
        requestReader = new RequestPostReader();

        Map<String, String> loginInfo = requestReader.readData(httpRequest);

        User validUser = null;
        try {
            validUser = userService.validLogin(loginInfo);
        } catch (FailLoggedException e) {
            logger.error("유효하지 않는 로그인 유저 요청");
            logger.error("/user/login_failed.html 로 redirect");
            return new HttpResponse.Builder()
                    .setData(new Data(dataOutputStream))
                    .setFileType(FileType.HTML)
                    .setHttpStatus(HttpStatus.RE_DIRECT)
                    .setRedirectUrl(new Url("/user/login_failed.html",RequestDataType.FILE))
                    .build();
        }

        Cookie cookie = sessionService.persistUser(validUser);


        logger.debug("로그인 성공한 userID:{}", validUser.getUserId());
        logger.debug("생성된 Cookie-sid:{}", cookie.getValue());

        return new HttpResponse.Builder()
                .setData(new Data(dataOutputStream))
                .setFileType(FileType.HTML)
                .setHttpStatus(HttpStatus.RE_DIRECT)
                .setRedirectUrl(new Url("/index.html",RequestDataType.FILE))
                .setCookie(cookie)
                .build();
    }

    @ControllerMethodInfo(path = "/user/login_failed.html", method = HttpMethod.GET)
    public HttpResponse loginFailed(DataOutputStream dataOutputStream, HttpRequest httpRequest) throws IOException {
        fileReader = new TemplatesFileReader();
        byte[] data = fileReader.readFile(httpRequest.getUrl());
        return new HttpResponse.Builder()
                .setData(new Data(dataOutputStream, data))
                .setFileType(FileType.HTML)
                .setHttpStatus(HttpStatus.OK)
                .build();
    }

    @ControllerMethodInfo(path = "/user/login.html", method = HttpMethod.GET)
    public HttpResponse userLoginHtml(DataOutputStream dataOutputStream, HttpRequest httpRequest) throws IOException {
        fileReader = new TemplatesFileReader();
        byte[] data = fileReader.readFile(httpRequest.getUrl());
        return new HttpResponse.Builder()
                .setData(new Data(dataOutputStream, data))
                .setFileType(FileType.HTML)
                .setHttpStatus(HttpStatus.OK)
                .build();
    }

    @ControllerMethodInfo(path = "/user/form.html", method = HttpMethod.GET)
    public HttpResponse userFormHtml(DataOutputStream dataOutputStream, HttpRequest httpRequest) throws IOException {
        fileReader = new TemplatesFileReader();
        byte[] data = fileReader.readFile(httpRequest.getUrl());
        return new HttpResponse.Builder()
                .setData(new Data(dataOutputStream, data))
                .setFileType(FileType.HTML)
                .setHttpStatus(HttpStatus.OK)
                .build();
    }

    @Auth
    @ControllerMethodInfo(path = "/user/list\\.html|/user/list", method = HttpMethod.GET)
    public HttpResponse userListHtml(DataOutputStream dataOutputStream, HttpRequest httpRequest) throws IOException {
        fileReader = new TemplatesFileReader();
        byte[] userListHtml = fileReader.readFile(httpRequest.getUrl());
        byte[] fixedUserListHtml = userRender.addUserList(userListHtml, userDatabase.findAll());
        return new HttpResponse.Builder()
                .setData(new Data(dataOutputStream, fixedUserListHtml))
                .setFileType(FileType.HTML)
                .setHttpStatus(HttpStatus.OK)
                .build();
    }

}

