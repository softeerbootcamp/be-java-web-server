package service;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.StringParser;
import webserver.RequestHandler;

import java.util.HashMap;
import java.util.Map;
public class JoinService {

    private User user;
    private String userId;
    private static final Logger logger = LoggerFactory.getLogger(JoinService.class);
    private Map<String,String> map = new HashMap<>();

    public String joinUser(String parseData) {
        userId = "";
        Map<String, String> signInInfo = StringParser.dataParsing(parseData);
        User user = new User(signInInfo.get("userId"),signInInfo.get("password")
                ,signInInfo.get("name"),signInInfo.get("email"));
        Database.addUser(user);
        return userId;
    }
}
