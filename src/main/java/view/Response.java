package view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpStatus;
import util.Redirect;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Response {
    private static final Logger logger = LoggerFactory.getLogger(Response.class);
    DataOutputStream dos;

    public Response(DataOutputStream dos){
        this.dos = dos;
    }
    public void response(byte[] body, RequestHeaderMessage requestHeaderMessage, HttpStatus httpStatus, Map<String,String> header){
        responseHeader(requestHeaderMessage,body,httpStatus,header);
        responseBody(body);
    }

    private void responseHeader(RequestHeaderMessage requestHeaderMessage, byte[] body, HttpStatus httpStatus, Map<String,String> header){
        try {
            dos.writeBytes("HTTP/1.1 " + httpStatus.toString() + "\r\n");
            dos.writeBytes("Content-Type: "+requestHeaderMessage.getContentType()+";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + body.length + "\r\n");
            for (Map.Entry<String,String> entry: header.entrySet()){
                dos.writeBytes(entry.getKey()+": "+entry.getValue());
            }
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
