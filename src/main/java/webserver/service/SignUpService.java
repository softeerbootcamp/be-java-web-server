package webserver.service;

import customException.AlreadyHasSameIdException;
import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.httpUtils.Request;
import webserver.httpUtils.Response;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SignUpService implements Service{

    private static final Logger logger = LoggerFactory.getLogger(SignUpService.class);
    static final String USER_ID = "userId";
    static final String USER_PASS_WORD = "password";
    static final String USER_NAME = "name";
    static final String USER_EMAIL = "email";
    @Override
    public Response exec(Request req) {
        Map<String, String> userInfo = getUserInfo(req);
        User newUser = new User(userInfo.get(USER_ID), userInfo.get(USER_PASS_WORD), userInfo.get(USER_NAME), userInfo.get(USER_EMAIL));
        try
        {
            if(null != Database.findUserById(newUser.getUserId()))
                throw new AlreadyHasSameIdException("이미 같은 아이디의 유저가 있음");
            Database.addUser(newUser);
        }catch (AlreadyHasSameIdException e)
        {
            // TODO 같은아이디 회원가입 예외처리
            logger.error(e.getMessage());
        }

        return null;
    }

    private Map<String, String> getUserInfo(Request req)
    {
        Map<String, String> parsedInfo = new HashMap<>();

        String unparsedInfo = new String(req.getReqBody().getContext());
        String tokens[] = unparsedInfo.split("&");
        Arrays.stream(tokens).forEach(
                elem -> {
                    String keyVal[] = elem.split("=");
                    parsedInfo.put(keyVal[0], keyVal[1]);
                }
        );

        return parsedInfo;
    }
}
