package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpStatus;
import view.RequestHeaderMessage;

import view.RequestMessage;
import view.Response;
import webserver.RequestHandler;

import java.io.DataOutputStream;
import java.io.OutputStream;

public class StaticController implements Controller{

    private static StaticController staticController;

    private static final Logger logger = LoggerFactory.getLogger(StaticController.class);
    private static final String RELATIVE_PATH = "./src/main/resources";
    private static final String STATIC = "/static";
    private static final String TEMPLATES = "/templates";

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

    private String getSubPath(RequestMessage requestMessage){
        if (requestMessage.getRequestHeaderMessage().getFileExtension().contains("html"))
            return TEMPLATES;
        return STATIC;
    }
    @Override
    public void control(RequestMessage requestMessage, OutputStream out) {
        String fileURL = RELATIVE_PATH + getSubPath(requestMessage) + requestMessage.getRequestHeaderMessage().getHttpOnlyURL();
        byte[] body = getBodyFile(fileURL);
        HttpStatus httpStatus = setHttpStatus(body);
        Response response = new Response(new DataOutputStream(out));
        response.response(body,requestMessage.getRequestHeaderMessage(), httpStatus);
    }


}
