package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Path;
import java.util.Objects;

import Request.HttpRequest;
import Response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.*;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            HttpRequest httpRequest = HttpRequestUtil.parseRequest(in);
            Path path = FileIoUtil.mappingPath(httpRequest.getPath());

            if(Objects.nonNull(httpRequest.getParams()))
                ManageDB.saveUser(httpRequest.getParams());

            HttpResponse httpResponse = HttpResponseUtil.makeResponse(httpRequest, path);
            DataOutputStream dos = new DataOutputStream(out);
            dos.writeBytes(httpResponse.toString());
            logger.debug("response: \n"+httpResponse.toString());
            responseBody(dos, httpResponse.getBody());

//            if(Objects.nonNull(path))
//                HttpResponseUtil.response(out, path);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (NullPointerException e){
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        if(Objects.isNull(body))
            throw new NullPointerException();
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
