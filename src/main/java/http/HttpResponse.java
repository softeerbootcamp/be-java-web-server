package http;

import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String requestFilePath = "./src/main/resources/templates/";

    private HttpStatus status;
    private HttpHeader header;
    private byte[] body;

    private String uri;

    public HttpResponse(HttpStatus status, byte[] body){
        this.status = status;
        this.body = body;
        if(status.equals(HttpStatus.OK)) makeResponse200Header();
    }

    private void makeResponse200Header(){
        List<String> headerLines = new ArrayList<>();
        headerLines.add("Content-Type: text/html;charset=utf-8" + System.lineSeparator());
        headerLines.add("Content-Length: " + body.length + System.lineSeparator());
        this.header = new HttpHeader(headerLines);
    }

    public static HttpHeader addHeader() {
        //TODO refactor
        return new HttpHeader(new ArrayList<>());
    }

    public static byte[] makeBody(HttpRequest httpRequest) throws IOException {
        return Files.readAllBytes(new File(requestFilePath + httpRequest.getUri()).toPath());
    }

    public void send(DataOutputStream dos) throws IOException {
        responseHeader(dos);
        responseBody(dos);
    }

    private void responseHeader(DataOutputStream dos) {
        try {
//            dos.writeBytes("HTTP/1.1 200 OK \r\n");
//            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
//            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
//            dos.writeBytes("\r\n");

            dos.writeBytes("HTTP/1.1 " + status.toString() + System.lineSeparator());
            dos.writeBytes(header.toString());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
