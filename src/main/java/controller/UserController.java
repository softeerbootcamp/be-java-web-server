package controller;


import controller.annotation.ControllerInfo;
import controller.annotation.ControllerMethodInfo;
import db.Database;
import db.UserDatabase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@ControllerInfo(regex = "^/user/[^\\.]*$")
public class UserController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private Database userDatabase = new UserDatabase();
    private Service userService = new UserService();
    private RequestReader requestReader;


    @ControllerMethodInfo(path = "/user/create", type = RequestDataType.IN_BODY, method = HttpMethod.POST)
    public HttpResponse UserQueryString(DataOutputStream dataOutputStream, HttpRequest httpRequest) throws IOException {
        requestReader = new RequestPostReader();

        HashMap<String, String> userMap = requestReader.readData(httpRequest);
        User user = (User) userService.createModel(userMap);
        userDatabase.addData(user);

        HttpResponse httpResponse = new HttpResponse(new response.Data(dataOutputStream), FileType.HTML, HttpStatus.RE_DIRECT);

        logger.debug("저장된 user:{}", userDatabase.findAll());

        return httpResponse;
    }


}

