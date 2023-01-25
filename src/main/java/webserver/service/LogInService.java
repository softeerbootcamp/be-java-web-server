package webserver.service;

import customException.cannotLogIn.CannotLogInException;
import customException.cannotLogIn.NotFoundUserException;
import customException.cannotLogIn.PasswordMismatchException;
import db.mysql.DB_Users;
import db.UserIdSession;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.constants.Paths;
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
            User whoIsTryingLogIn = DB_Users.findUserById(userInfo.get(User.ID));

            if(whoIsTryingLogIn == DB_Users.NOT_FOUND_USER)
                throw new NotFoundUserException("가입하지 않은 아이디입니다.");
            if(!userInfo.get(User.PASS_WORD).equals(whoIsTryingLogIn.getPassword()))
                throw new PasswordMismatchException("비밀번호를 다시 입력하세요");

        } catch(CannotLogInException e)
        {
            return new Response()
                    .withVersion(req.getReqLine().getVersion())
                    .withStatCodeAndText(302, "REDIR")
                    .withHeaderKeyVal("Location", Paths.LOGIN_FAIL_PATH);
        }

        sid = UserIdSession.addUserId(userInfo.get(User.ID));
        logger.debug("id : " + userInfo.get(User.ID) + ", passw : " + userInfo.get(User.PASS_WORD));
        return new Response()
                .withVersion(req.getReqLine().getVersion())
                .withStatCodeAndText(302, "FOUND")
                .withHeaderKeyVal("Set-Cookie", "sid=" + sid + "; Path=/")
                .withHeaderKeyVal("Location", Paths.HOME_PATH);
    }
}
