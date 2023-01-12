package response;

import enums.ContentTypeEnum;
import enums.ControllerTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Response {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private DataOutputStream dos;
    public Response(OutputStream outputStream){
        this.dos = new DataOutputStream(outputStream);

    }
    public void responseMaker(ControllerTypeEnum controllerTypeEnum, ContentTypeEnum contentTypeEnum,
                              int lengthOfBodyContent,String redirectUrl){
        try {
            ResponseStatusLine responseStatusLine = new ResponseStatusLine(controllerTypeEnum);
            dos.writeBytes(responseStatusLine.getResponseStatusLine()+" \r\n");
            dos.writeBytes("Content-Type: "+contentTypeEnum.getValue()+";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            if(responseStatusLine.getStatusCodeWithMessage().contains("302")){
                dos.writeBytes("Location : "+redirectUrl+"\r\n");
            }
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
    public void response200Header(int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
    public void response302Header() {
        try {
            dos.writeBytes("HTTP/1.1 302 FOUND \r\n");
            dos.writeBytes("Location: /index.html\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
    public void responseCssHeader(int lengthOfBodyContent){
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/css;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
    public void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
    public void responseBodyTemp() {
        try {
            //dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
