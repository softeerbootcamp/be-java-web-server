package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpStatus;
import view.RequestHeaderMessage;
import view.Response;
import webserver.RequestHandler;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.HashMap;

//Todo: 일단 tmpelates와 static controller를 나누긴 하였는데, 공통 기능이 대부분이라서 추후 합쳐질 수도 있음
public class TemplatesController implements Controller{
    private static final Logger logger = LoggerFactory.getLogger(TemplatesController.class);
    private static final String RELATIVE_PATH = "./src/main/resources/templates";
    byte[] body = new byte[0];
    private HttpStatus httpStatus = HttpStatus.ClientError;
    private OutputStream out;
    private RequestHeaderMessage requestHeaderMessage;
    public TemplatesController(RequestHeaderMessage requestHeaderMessage, OutputStream out){
        String fileURL = RELATIVE_PATH + requestHeaderMessage.getHttpOnlyURL();
        this.requestHeaderMessage = requestHeaderMessage;
        this.out = out;
        body = getBodyFile(fileURL);
        httpStatus = setHttpStatus(body);
    }
    @Override
    public void control() {
        Response response = new Response(new DataOutputStream(out));
        response.response(body,requestHeaderMessage, httpStatus);
    }

    public String toString(){
        return "TemplatesController: request " + requestHeaderMessage.getHttpOnlyURL();
    }
}
