package webserver.service;

import customException.AlreadyHasSameIdException;
import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.constants.Paths;
import webserver.httpUtils.Request;
import webserver.httpUtils.Response;

import java.util.Map;

public class SignUpService implements Service{

    private static final Logger logger = LoggerFactory.getLogger(SignUpService.class);
    @Override
    public Response exec(Request req) {
        Map<String, String> userInfo = getUserInfo(req);
        User newUser = new User(userInfo.get(User.ID), userInfo.get(User.PASS_WORD), userInfo.get(User.NAME), userInfo.get(User.EMAIL));
        try
        {
            if(Database.findUserById(newUser.getUserId()) != Database.NOT_FOUND_USER)
                throw new AlreadyHasSameIdException("이미 같은 아이디의 유저가 있음");
            Database.addUser(newUser);
        }catch (AlreadyHasSameIdException e)
        {
            logger.error(e.getMessage());
            return new Response()
                .withVersion(req.getReqLine().getVersion())
                .withStatCodeAndText(302, "FOUND")
                .withHeaderKeyVal("Location", Paths.ENROLL_FAIL_PATH);
        }

        return new Response()
                .withVersion(req.getReqLine().getVersion())
                .withStatCodeAndText(302, "FOUND")
                .withHeaderKeyVal("Location", Paths.HOME_PATH);
    }
}
