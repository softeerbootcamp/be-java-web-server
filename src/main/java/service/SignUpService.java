package service;

import db.Database;
import model.User;
import webserver.HttpRequest;
import webserver.HttpResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SignUpService {

    public void signUp(HttpRequest request){
        String userId = request.getQueryByKey("userId");
        String password = request.getQueryByKey("password");
        String name = request.getQueryByKey("name");
        String email = request.getQueryByKey("email");//1234%40khu.ac.kr
        //email.replace("%40","@");
        saveNewUser(userId,password,name,email);
    }

    public void singUpByPost(HttpRequest request) {
        try{
            System.out.println(request.parseBody());
            Map<String,String> formInfo = request.parseBody();
            String userId = formInfo.get("userId");
            String password = formInfo.get("password");
            String name = formInfo.get("name");
            String email = formInfo.get("email");//1234%40khu.ac.kr
            saveNewUser(userId,password,name,email);
        }catch(IOException e){
            System.out.println("error in body parsing");
        }

    }

    public User createUser(String userId,String password,String name,String email){
        return User.of(userId,password,name,email);
    }

    public void saveNewUser(String userId,String password,String name,String email){
        Database.addUser(createUser(userId,password,name,email));
    }

}
