package controller;

import model.domain.User;
import model.repository.MemoryUserRepository;
import model.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.MessageParser;
import util.HttpStatus;
import util.Redirect;
import util.Session;
import view.RequestBodyMessage;
import view.RequestHeaderMessage;
import view.RequestMessage;
import view.Response;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class UserController implements Controller{
    static UserController userController;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String EMAIL = "email";

    private HttpStatus httpStatus = HttpStatus.ClientError;

    public static UserController getInstance(){
        if (userController != null)
            return userController;
        return userController = new UserController();
    }

    @Override
    public void control(RequestMessage requestMessage, OutputStream out) {
        UserService userService = new UserService(new MemoryUserRepository());
        byte[] body = new byte[0];
        Map<String,String> headerKV= new HashMap<>();
        httpStatus = HttpStatus.ClientError;
        userCommand(requestMessage,userService, headerKV);
        Response response = new Response(new DataOutputStream(out));
        response.response(body,requestMessage.getRequestHeaderMessage(), httpStatus, headerKV);
    }

    public void userCommand(RequestMessage requestMessage, UserService userService, Map<String,String> headerKV){
        if (requestMessage.getRequestHeaderMessage().getRequestAttribute().equals("/create")){
            userCreate(requestMessage, userService, headerKV);
            return;
        }
        if (requestMessage.getRequestHeaderMessage().getRequestAttribute().equals("/login")){
            userLogin(requestMessage, userService, headerKV);
            return;
        }
    }

    private void userCreate(RequestMessage requestMessage, UserService userService, Map<String, String> headerKV){
        Map<String,String> userInfo;
        if (requestMessage.getRequestBodyMessage().getQueryString().equals(""))
            userInfo = MessageParser.parseQueryString(requestMessage.getRequestHeaderMessage().getHttpReqParams());
        else userInfo = MessageParser.parseQueryString(requestMessage.getRequestBodyMessage().getQueryString());
        try{
            userService.join(new User(userInfo.get(USER_ID),userInfo.get(PASSWORD),userInfo.get(NAME),userInfo.get(EMAIL)));
            setLocation(Redirect.getRedirectLink(requestMessage.getRequestHeaderMessage().getRequestAttribute()), headerKV);
        } catch (IllegalStateException e){
            setLocation("/user/form_failed.html", headerKV);
            logger.debug(e.getMessage());
        }
    }

    private void userLogin(RequestMessage requestMessage, UserService userService, Map<String,String> headerKV){
        Map<String,String> loginInfo;
        loginInfo = MessageParser.parseQueryString(requestMessage.getRequestBodyMessage().getQueryString());
        try {
            User user = userService.login(loginInfo.get(USER_ID), loginInfo.get(PASSWORD));
            setLocation(Redirect.getRedirectLink(requestMessage.getRequestHeaderMessage().getRequestAttribute()), headerKV);
            setCookie(Session.newLoginSession(user), headerKV);
        } catch (IllegalStateException e){
            setLocation("/user/login_failed.html", headerKV);
            logger.debug(e.getMessage());
        }
    }

    private void setCookie(String sid, Map<String,String> headerKV){
        logger.debug("sid: "+sid);
        setHeader(headerKV, "Set-Cookie","sid="+sid+"; Path=/");
    }

    private void setLocation(String redirectLink, Map<String,String> headerKV){
        if (!redirectLink.equals("")){
            httpStatus = HttpStatus.Redirection;
            setHeader(headerKV,"Location", redirectLink);
        }
    }

    private void setHeader(Map<String,String> headerKV, String key, String value){
        headerKV.put(key,value);
    }

}
