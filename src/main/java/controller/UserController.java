package controller;

import model.domain.User;
import model.repository.MemoryUserRepository;
import model.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.MessageParser;
import util.HttpStatus;
import util.Redirect;
import view.RequestBodyMessage;
import view.RequestHeaderMessage;
import view.RequestMessage;
import view.Response;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class UserController implements Controller{
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private Map<String,String> headerKV= new HashMap<>();
    byte[] body = new byte[0];
    private HttpStatus httpStatus = HttpStatus.ClientError;
    private RequestMessage requestMessage;
    private RequestHeaderMessage requestHeaderMessage;
    private RequestBodyMessage requestBodyMessage;
    private UserService userService = new UserService(new MemoryUserRepository());
    private OutputStream out;
    public UserController(){};
    public UserController(RequestMessage requestMessage, OutputStream out){
        this.requestMessage = requestMessage;
        this.requestHeaderMessage = requestMessage.getRequestHeaderMessage();
        this.requestBodyMessage = requestMessage.getRequestBodyMessage();
        this.out = out;
    }
    @Override
    public void control() {
        userCommand();
        Response response = new Response(new DataOutputStream(out));
        response.response(body,requestHeaderMessage, httpStatus, headerKV);
    }

    public void userCommand(){
        if (requestHeaderMessage.getRequestAttribute().equals("/create")){
            userCreate();
            return;
        }
    }

    private void userCreate(){
        Map<String,String> userInfo;
        if (requestBodyMessage.getQueryString().equals(""))
            userInfo = MessageParser.parseQueryString(requestHeaderMessage.getHttpReqParams());
        else userInfo = MessageParser.parseQueryString(requestBodyMessage.getQueryString());
        try{
            userService.join(new User(userInfo.get(USER_ID),userInfo.get(PASSWORD),userInfo.get(NAME),userInfo.get(EMAIL)));
            setLocation(Redirect.getRedirectLink(requestHeaderMessage.getHttpOnlyURL()));
        } catch (IllegalStateException e){
            setLocation("/user/form_failed.html");
            logger.debug(e.getMessage());
        }
    }

    private void setLocation(String redirectLink){
        if (!redirectLink.equals("")){
            httpStatus = HttpStatus.Redirection;
            headerKV.put("Location",redirectLink);
        }
    }

    public String toString(){
        return "UserController: request " + requestHeaderMessage.getHttpOnlyURL();
    }

}
