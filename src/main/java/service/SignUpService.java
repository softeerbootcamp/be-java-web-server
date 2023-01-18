package service;

import db.Database;
import model.User;
import webserver.HttpRequest;
import webserver.HttpResponse;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class SignUpService {

    private static final String ENCODE_CHARSET = "UTF-8";


    public void signUp(HttpRequest request) throws UnsupportedEncodingException {
        String userId = request.getQueryByKey("userId");
        String password = request.getQueryByKey("password");
        String name = request.getQueryByKey("name");
        URLDecoder.decode(name, ENCODE_CHARSET);
        String email = request.getQueryByKey("email");
        URLDecoder.decode(email, ENCODE_CHARSET);
        saveNewUser(userId,password,name,email);
    }

    public void singUpByPost(HttpRequest request) throws UnsupportedEncodingException {
        Map<String,String> formInfo = request.parseBody();
        String userId = formInfo.get("userId");
        String password = formInfo.get("password");
        String name = formInfo.get("name");
        URLDecoder.decode(name, ENCODE_CHARSET);
        String email = formInfo.get("email");
        URLDecoder.decode(email, ENCODE_CHARSET);
        saveNewUser(userId,password,name,email);
    }

    private User createUser(String userId,String password,String name,String email){
        return User.of(userId,password,name,email);
    }

    private void saveNewUser(String userId,String password,String name,String email){
        Database.addUser(createUser(userId,password,name,email));
    }

}
