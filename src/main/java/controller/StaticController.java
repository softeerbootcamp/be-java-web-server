package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpStatus;
import view.RequestHeaderMessage;
import view.Response;
import webserver.RequestHandler;

import java.io.DataOutputStream;
import java.io.OutputStream;

public class StaticController implements Controller{
    private static final Logger logger = LoggerFactory.getLogger(StaticController.class);
    private static final String RELATIVE_PATH = "./src/main/resources/static";
    byte[] body = new byte[0];
    private HttpStatus httpStatus = HttpStatus.ClientError;
    private OutputStream out;
    private RequestHeaderMessage requestHeaderMessage;
    public StaticController(RequestHeaderMessage requestHeaderMessage, OutputStream out){
        String fileURL = RELATIVE_PATH + requestHeaderMessage.getHttpOnlyURL();
        this.out = out;
        this.requestHeaderMessage = requestHeaderMessage;
        body = getBodyFile(fileURL);
        httpStatus = setHttpStatus(body);
    }
    @Override
    public void control() {
        Response response = new Response(new DataOutputStream(out));
        response.response(body,requestHeaderMessage, httpStatus);
    }

    public String toString(){
        return "StaticController: request " + requestHeaderMessage.getHttpOnlyURL();
    }
}
