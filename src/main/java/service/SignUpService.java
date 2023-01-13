package service;

import db.Database;
import model.User;
import webserver.HttpRequest;
import webserver.HttpResponse;

public class SignUpService {

    public void signUp(HttpRequest request, HttpResponse response){
        String userId = request.getQueryByKey("userId");
        String password = request.getQueryByKey("password");
        String name = request.getQueryByKey("name");
        String email = request.getQueryByKey("email");//1234%40khu.ac.kr
        //email.replace("%40","@");
        User user = User.of(userId,password,name,email);
        Database.addUser(user);
    }

}
