package controller;


import controller.annotation.ControllerInfo;
import controller.annotation.ControllerMethodInfo;
import db.Database;
import db.UserDatabase;
import model.Session;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reader.fileReader.FileReader;
import reader.fileReader.TemplatesFileReader;
import reader.requestReader.RequestPostReader;
import reader.requestReader.RequestReader;
import request.HttpRequest;
import request.RequestDataType;
import response.HttpResponse;
import service.Service;
import service.UserService;
import util.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

@ControllerInfo(regex = ".*\\/user.*" )
public class UserController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private Database userDatabase = new UserDatabase();
    private Service userService = new UserService();
    private RequestReader requestReader;
    private FileReader fileReader;


    @ControllerMethodInfo(path = "/user/create", type = RequestDataType.IN_BODY, method = HttpMethod.POST)
    public HttpResponse UserQueryString(DataOutputStream dataOutputStream, HttpRequest httpRequest) throws IOException {
        requestReader = new RequestPostReader();

        HashMap<String, String> userMap = requestReader.readData(httpRequest);
        User user = (User) userService.createModel(userMap);
        userDatabase.addData(user);

        Cookie cookie = new Cookie(Session.SESSION_ID, Session.makeSessionId());
        HttpResponse httpResponse = new HttpResponse(new response.Data(dataOutputStream), FileType.HTML, HttpStatus.RE_DIRECT
                ,cookie);

        logger.debug("저장된 user:{}", userDatabase.findObjectById(user.getUserId()));
        logger.debug("생성된 Cookie-sid:{}",cookie.getValue() );

        return httpResponse;
    }

    @ControllerMethodInfo(path = "/user/form.html", type = RequestDataType.TEMPLATES_FILE, method = HttpMethod.GET)
    public HttpResponse userFormHtml(DataOutputStream dataOutputStream, HttpRequest httpRequest) throws IOException {
        fileReader = new TemplatesFileReader();
        byte[] data = new byte[0];
        data = fileReader.readFile(httpRequest.getUrl());
        return new HttpResponse(new response.Data(dataOutputStream, data), FileType.getFileType(httpRequest.getUrl()), HttpStatus.OK);
    }


}

