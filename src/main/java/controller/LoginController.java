package controller;

import db.Database;
import model.User;
import request.HttpRequest;
import response.HttpResponse;
import service.LoginService;
import session.HttpSession;
import session.HttpSessionManager;
import view.Model;

public class LoginController implements Controller{

    private static final String REDIRECT_URL ="/index.html";
    private static final String FAILED_REDIRECT_URL ="/user/login_failed.html";
    private static final String LOGINED_ATTR_KEY = "sid";
    private static LoginController instance;
    private final LoginService loginService;

    public static LoginController getInstance(){
        if(instance == null){
            synchronized (LoginController.class){
                instance = new LoginController();
            }
        }
        return instance;
    }

    public LoginController(){
        loginService = LoginService.getInstance();
    }


    @Override
    public String doPost(HttpRequest request, HttpResponse response, Model model) {
        String userId = request.parseBody().get("userId");
        String password = request.parseBody().get("password");
        doLogin(userId,password,response);

        return "";
    }

    private void doLogin(String userId, String password,HttpResponse response){
        try{
            boolean alreadyUser = loginService.checkRightUser(userId,password);
            if(alreadyUser){
                User user = Database.findUserById(userId);
                HttpSession session = HttpSessionManager.createSession(user);
                HttpSessionManager.addSession(session);
                response.addCookie(LOGINED_ATTR_KEY, session.getId());
                response.redirect(REDIRECT_URL);
            } else {
                response.redirect(FAILED_REDIRECT_URL);
            }
        }catch(NullPointerException e){
            System.out.println("no user exist");
            NotFoundExceptionHandler.showErrorPage(response);
        }
    }

}
