package controller;


import db.Database;
import db.UserDatabase;
import model.User;
import reader.RequestGetReader;
import reader.RequestReader;
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

    private final static String RE_DIRECT_URL = "/index.html";
    private Database userDatabase = new UserDatabase();
    private Service userService = new UserService();


    private RequestReader requestReader;
    public HttpResponse UserQueryString(DataOutputStream dataOutputStream, HttpRequest httpRequest) throws IOException {
        requestReader = new RequestGetReader();

        HashMap<String, String> userMap = requestReader.readQueryParameter(httpRequest);
        User user = (User) userService.createModel(userMap);
        userDatabase.addData(user);

        HttpResponse httpResponse = new HttpResponse(new Data(dataOutputStream), FileType.HTML, HttpStatus.RE_DIRECT);
//        httpResponse.updateRedirectUrl(RE_DIRECT_URL);

        System.out.println("userDatabase.findAll() = " + userDatabase.findAll());


        return httpResponse;
    }


}
