package webserver.domain.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;
import webserver.domain.StatusCodes;
import webserver.domain.request.RequestLine;
import webserver.exception.HttpRequestException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Response {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private OutputStream out;

    public Response(OutputStream out){
        this.out = out;
    }

    //Make Http response header for every error
    public void responseHeader(DataOutputStream dos, String errorCode, String errorMsg, int lengthOfBodyContent) throws IOException {
        String responseLine = "HTTP/1.1 " + errorCode + " " + errorMsg + " \r\n";
        dos.writeBytes(responseLine);
        dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
        dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
        dos.writeBytes("\r\n");
    }

    public void responseBody(DataOutputStream dos,byte[] body) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }

    public void makeResponse(byte[] body, String statusCode, String msg)  {
        DataOutputStream dos = new DataOutputStream(out);
        try {
            responseHeader(dos, statusCode, msg, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.debug(e.getMessage());
        }
    }
}
