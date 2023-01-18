package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpStatus;

import view.RequestMessage;
import view.Response;

import java.io.DataOutputStream;
import java.io.OutputStream;

public class StaticController implements Controller{

    private static StaticController staticController;

    private static final Logger logger = LoggerFactory.getLogger(StaticController.class);
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
        byte[] body = getResponseBody(requestMessage);
        HttpStatus httpStatus = setHttpStatus(body);
        Response response = new Response(new DataOutputStream(out));
        response.response(body,requestMessage.getRequestHeaderMessage(), httpStatus);
    }

    public byte[] getResponseBody(RequestMessage requestMessage){
        String fileURL = RELATIVE_PATH + requestMessage.getRequestHeaderMessage().getSubPath() + requestMessage.getRequestHeaderMessage().getHttpOnlyURL();
        return getBodyFile(fileURL);
    }


}
