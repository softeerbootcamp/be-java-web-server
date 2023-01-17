package service;

import db.Database;
import model.User;
import webserver.HttpRequest;
import webserver.HttpResponse;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class SignUpService {

    public void signUp(HttpRequest request){
        String userId = request.getQueryByKey("userId");
        String password = request.getQueryByKey("password");
        String name = request.getQueryByKey("name");
        String email = request.getQueryByKey("email");//1234%40khu.ac.kr
        URLDecoder.decode(email, StandardCharsets.UTF_8);
        saveNewUser(userId,password,name,email);
    }

    public void singUpByPost(HttpRequest request) {
        try{
            Map<String,String> formInfo = request.parseBody();
            String userId = formInfo.get("userId");
            String password = formInfo.get("password");
            String name = formInfo.get("name");
            String email = formInfo.get("email");
            URLDecoder.decode(email, StandardCharsets.UTF_8);
            saveNewUser(userId,password,name,email);
        }catch(IOException e){
            System.out.println("error in body parsing");
        }

    }

    private User createUser(String userId,String password,String name,String email){
        return User.of(userId,password,name,email);
    }

    private void saveNewUser(String userId,String password,String name,String email){
        Database.addUser(createUser(userId,password,name,email));
    }

}
