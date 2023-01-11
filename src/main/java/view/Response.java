package view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Redirect;

import java.io.DataOutputStream;
import java.io.IOException;

public class Response {
    private static final Logger logger = LoggerFactory.getLogger(Response.class);

    public void response(DataOutputStream dos, byte[] body, RequestHeaderMessage requestHeaderMessage){
        responseHeader(dos,body.length,requestHeaderMessage.getContentType(), requestHeaderMessage.getHttpOnlyURL());
        responseBody(dos, body);
    }
    private void responseHeader(DataOutputStream dos, int lengthOfBodyContent, String contentType, String onlyURL){
        String redirectLink = Redirect.getRedirectLink(onlyURL);
        if (redirectLink.equals(""))
            response200Header(dos,lengthOfBodyContent,contentType);
        else response302Header(dos, redirectLink);
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String contentType) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: "+contentType+";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, String redirectLink) {
        try {
            dos.writeBytes("HTTP/1.1 302 FOUND \r\n");
            dos.writeBytes("Location: "+redirectLink+"\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
