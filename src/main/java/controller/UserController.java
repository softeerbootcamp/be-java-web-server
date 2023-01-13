package controller;


import db.Database;
import db.UserDatabase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reader.RequestGetReader;
import reader.RequestReader;
import reader.fileReader.StaticFileReader;
import request.HttpRequest;
import response.Data;
import response.HttpResponse;
import service.Service;
import service.UserService;
import util.FileType;
import util.HttpStatus;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class UserController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    protected static String REGEX = ".*\\/user.*";
    private Database userDatabase = new UserDatabase();
    private Service userService = new UserService();
    private RequestReader requestReader;

    public HttpResponse UserQueryString(DataOutputStream dataOutputStream, HttpRequest httpRequest) throws IOException {
        requestReader = new RequestGetReader();

        HashMap<String, String> userMap = requestReader.readData(httpRequest);
        User user = (User) userService.createModel(userMap);
        userDatabase.addData(user);

        HttpResponse httpResponse = new HttpResponse(new Data(dataOutputStream), FileType.HTML, HttpStatus.RE_DIRECT);

        logger.info("저장된 user:{}", userDatabase.findAll());

        return httpResponse;
    }


}

