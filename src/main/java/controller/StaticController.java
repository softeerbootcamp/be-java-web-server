package controller;

import util.HttpStatus;

import util.Redirect;
import view.RequestMessage;
import view.Response;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class StaticController implements Controller{

    private static StaticController staticController;

    private static final String RELATIVE_PATH = "./src/main/resources";

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
        String uri = requestMessage.getRequestHeaderMessage().getHttpOnlyURL();
        String redirectLink = Redirect.getRedirectLink(uri);
        if (redirectLink.equals(""))
            return false;
        headerKV.put("Location",redirectLink);
        return true;
    }

}
