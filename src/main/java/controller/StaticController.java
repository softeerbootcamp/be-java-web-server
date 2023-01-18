package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpStatus;

import view.RequestMessage;
import view.Response;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class StaticController implements Controller{

    private static StaticController staticController;

    private static final Logger logger = LoggerFactory.getLogger(StaticController.class);
    private static final String RELATIVE_PATH = "./src/main/resources";

    private StaticController(){}

    private StaticController(){}

    public static StaticController getInstance(){
        if (staticController == null){
            synchronized (StaticController.class){
                if (staticController == null){
                    staticController = new StaticController();
                }
            }
        }
        return staticController;
    }

    @Override
    public void control(RequestMessage requestMessage, OutputStream out) {
        Response response = new Response(new DataOutputStream(out));
        byte[] body = getResponseBody(requestMessage);
        Map<String,String> headerKV = new HashMap<>();
        if (forbiddenAccess(requestMessage,headerKV)){
            response.response(body,requestMessage.getRequestHeaderMessage(),HttpStatus.Redirection,headerKV);
            return;
        }
        HttpStatus httpStatus = setHttpStatus(body);
        response.response(body,requestMessage.getRequestHeaderMessage(), httpStatus);
    }

    public byte[] getResponseBody(RequestMessage requestMessage){
        String fileURL = RELATIVE_PATH + requestMessage.getRequestHeaderMessage().getSubPath() + requestMessage.getRequestHeaderMessage().getHttpOnlyURL();
        return getBodyFile(fileURL);
    }

    private boolean forbiddenAccess(RequestMessage requestMessage, Map<String,String> headerKV){
        if (requestMessage.getRequestHeaderMessage().getHttpOnlyURL().startsWith("/user/list")){
            headerKV.put("Location","/user/login.html");
            return true;
        }
        return false;
    }


}
