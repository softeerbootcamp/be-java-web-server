package controller;


import controller.annotation.ControllerInfo;
import db.Database;
import db.UserDatabase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reader.RequestGetReader;
import reader.RequestPostReader;
import reader.RequestReader;
import request.HttpRequest;
import response.Data;
import response.HttpResponse;
import service.Service;
import service.UserService;
import util.FileType;
import util.HttpMethod;
import util.HttpStatus;
import util.UrlType;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class UserController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    protected static String REGEX = ".*\\/user.*";
    private Database userDatabase = new UserDatabase();
    private Service userService = new UserService();
    private RequestReader requestReader;


    @ControllerInfo(path = "/user/create", u = UrlType.NOTHING, method = HttpMethod.POST)
    public HttpResponse UserQueryString(DataOutputStream dataOutputStream, HttpRequest httpRequest) throws IOException {
        requestReader = new RequestPostReader();

        HashMap<String, String> userMap = requestReader.readData(httpRequest);
        User user = (User) userService.createModel(userMap);
        userDatabase.addData(user);

        HttpResponse httpResponse = new HttpResponse(new Data(dataOutputStream), FileType.HTML, HttpStatus.RE_DIRECT);

        logger.debug("저장된 user:{}", userDatabase.findAll());

        return httpResponse;
    }


}

