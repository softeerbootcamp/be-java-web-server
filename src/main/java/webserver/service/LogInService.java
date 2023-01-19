package webserver.service;

import customException.cannotLogIn.CannotLogInException;
import customException.cannotLogIn.NotFoundUserException;
import customException.cannotLogIn.PasswordMismatchException;
import db.Database;
import db.UserIdSession;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.Paths;
import webserver.httpUtils.Request;
import webserver.httpUtils.Response;

import java.util.Map;

public class LogInService implements Service{
    private static final Logger logger = LoggerFactory.getLogger(LogInService.class);

    @Override
    public Response exec(Request req) {
        Map<String, String> userInfo = getUserInfo(req);
        String sid = new String();

        try{
            User whoIsTryingLogIn = Database.findUserById(userInfo.get(User.ID));
            if(whoIsTryingLogIn == Database.NOT_FOUND_USER)
                throw new NotFoundUserException("가입하지 않은 아이디입니다.");
            if(userInfo.get(User.PASS_WORD) != whoIsTryingLogIn.getPassword())
                throw new PasswordMismatchException("비밀번호를 다시 입력하세요");
            sid = UserIdSession.addUserId(whoIsTryingLogIn.getUserId());
        } catch(CannotLogInException e)
        {

        }

        logger.debug("id : " + userInfo.get(User.ID) + ", passw : " + userInfo.get(User.PASS_WORD));
        return new Response()
                .withVersion(req.getReqLine().getVersion())
                .withStatCodeAndText(302, "FOUND")
                .withHeaderKeyVal("Location", Paths.HOME_PATH)
                .withHeaderKeyVal("Set-Cookie", "sid=" + sid + "; Path=/");
    }
}
