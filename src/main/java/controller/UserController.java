package controller;


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
import response.Data;
import response.HttpResponse;
import service.SessionService;
import service.UserService;
import util.*;
import util.error.erroclass.NotLoggedException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

@ControllerInfo(regex = ".*\\/user.*")
public class UserController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private Database userDatabase = new UserDatabase();
    private UserService userService = new UserService();


    private SessionService sessionService = new SessionService();

    private RequestReader requestReader;
    private FileReader fileReader;


    @ControllerMethodInfo(path = "/user/create", type = RequestDataType.IN_BODY, method = HttpMethod.POST)
    public HttpResponse UserQueryString(DataOutputStream dataOutputStream, HttpRequest httpRequest) throws IOException {
        requestReader = new RequestPostReader();

        Map<String, String> userMap = requestReader.readData(httpRequest);
        User user = (User) userService.createModel(userMap);
        userDatabase.addData(user);

        Cookie cookie = sessionService.persistUser(user);

        HttpResponse httpResponse = new HttpResponse(new Data(dataOutputStream), FileType.HTML, HttpStatus.RE_DIRECT
                , cookie);

        logger.debug("저장된 user:{}", userDatabase.findObjectById(user.getUserId()));
        logger.debug("생성된 Cookie-sid:{}", cookie.getValue());

        return httpResponse;
    }

    @ControllerMethodInfo(path = "/user/login", type = RequestDataType.IN_BODY, method = HttpMethod.POST)
    public HttpResponse login(DataOutputStream dataOutputStream, HttpRequest httpRequest) throws IOException, NotLoggedException {
        requestReader = new RequestPostReader();

        Map<String, String> loginInfo = requestReader.readData(httpRequest);

        User validUser = userService.validLogin(loginInfo);

        Cookie cookie = sessionService.persistUser(validUser);

        HttpResponse httpResponse = new HttpResponse(new Data(dataOutputStream), FileType.HTML, HttpStatus.RE_DIRECT
                , cookie);

        logger.debug("로그인 성공한 userID:{}", validUser.getUserId());
        logger.debug("생성된 Cookie-sid:{}", cookie.getValue());

        return httpResponse;
    }

    @ControllerMethodInfo(path = "/user/login.html", type = RequestDataType.TEMPLATES_FILE, method = HttpMethod.GET)
    public HttpResponse userLoginHtml(DataOutputStream dataOutputStream, HttpRequest httpRequest) throws IOException {
        fileReader = new TemplatesFileReader();
        byte[] data = fileReader.readFile(httpRequest.getUrl());
        HttpResponse httpResponse = new HttpResponse(new Data(dataOutputStream,data), FileType.HTML, HttpStatus.OK);
        return httpResponse;
    }

    @ControllerMethodInfo(path = "/user/form.html", type = RequestDataType.TEMPLATES_FILE, method = HttpMethod.GET)
    public HttpResponse userFormHtml(DataOutputStream dataOutputStream, HttpRequest httpRequest) throws IOException {
        fileReader = new TemplatesFileReader();
        byte[] data = new byte[0];
        data = fileReader.readFile(httpRequest.getUrl());
        return new HttpResponse(new Data(dataOutputStream, data), FileType.getFileType(httpRequest.getUrl()), HttpStatus.OK);
    }






}

