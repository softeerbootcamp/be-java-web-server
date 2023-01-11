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
    private static final String basePath = "./src/main/resources";
    private static final String htmlFilePath = "/templates";
    private static final String staticFilePath = "/static";

    private HttpStatus status;
    private HttpHeader header;
    private byte[] body;

    private String contentType;
    private String uri;

    public HttpResponse(HttpStatus status, byte[] body, String contentType){
        this.status = status;
        this.body = body;
        this.contentType = contentType;
        if(status.equals(HttpStatus.OK)) makeResponse200Header();
    }

    public static String makeFilePath(String contentType) {
        String filePath = "";
        if(contentType.equals("text/html")){
            return basePath + htmlFilePath;
        }
        return basePath + staticFilePath;
    }

    private void makeResponse200Header(){
        List<String> headerLines = new ArrayList<>();
        logger.debug("contentType: {}", contentType);
        headerLines.add("Content-Type: " + contentType + ";charset=utf-8" + System.lineSeparator());
        headerLines.add("Content-Length: " + body.length + System.lineSeparator());
        this.header = new HttpHeader(headerLines);
    }

    public static HttpHeader addHeader() {
        //TODO refactor
        return new HttpHeader(new ArrayList<>());
    }

    public static byte[] makeBody(HttpRequest httpRequest, String filePath) throws IOException {
        logger.debug("filePath get : {}", filePath + httpRequest.getUri());
        return Files.readAllBytes(new File(filePath + httpRequest.getUri()).toPath());
    }

    public void send(DataOutputStream dos) throws IOException {
        responseHeader(dos);
        responseBody(dos);
    }

    private void responseHeader(DataOutputStream dos) {
        try {
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
