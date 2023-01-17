package controller;

import service.LoginService;
import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.HttpSession;
import webserver.HttpSessionManager;

import java.util.UUID;

public class LoginController implements Controller{

    private static final String REDIRECT_URL ="/index.html";
    private static final String FAILED_REDIRECT_URL ="/user/login_failed.html";
    private static final String LOGINED_ATTR_KEY = "sid";
    private LoginService service= new LoginService();

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        //System.out.println(request.parseBody().entrySet());
        String userId = request.parseBody().get("userId");
        String password = request.parseBody().get("password");
        boolean alreadyUser = service.checkRightUser(userId,password);
        if(alreadyUser){
            HttpSession session = HttpSessionManager.createSession(request.parseBody());
            HttpSessionManager.addSession(session);
            response.addCookie(LOGINED_ATTR_KEY, session.getId());
            response.redirect(REDIRECT_URL);
        }
        else response.redirect(FAILED_REDIRECT_URL);
    }

}
