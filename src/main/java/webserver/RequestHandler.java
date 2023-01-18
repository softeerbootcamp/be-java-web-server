package webserver;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import response.HttpResponse;
import response.ResponseHandler;
import response.StatusLine;
import util.FileIoUtils;

public class RequestHandler implements Runnable{

    private static final String lineSeparate = System.lineSeparator();
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final Socket connection; // Client
    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());


        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            /* TODO
            *   - in : 서버 -> 클라이언트로 응답을 보내는 데이터를 싣음
            * */

            HttpResponse httpResponse = ResponseHandler.controlRequestAndResponse(HttpRequest.of(in));
            respondToHttpRequest(out, httpResponse);
        } catch (IOException | URISyntaxException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.error(e.getMessage());
        }
    }

    private void respondToHttpRequest(OutputStream out, HttpResponse httpResponse) {
        DataOutputStream dos = new DataOutputStream(out);
        success(dos,httpResponse);
        redirect(dos,httpResponse);
    }

    public void success(DataOutputStream dos, HttpResponse httpResponse) {
        if(httpResponse.getStatusLine().equals(StatusLine.OK)
        ){
            httpResponse.respond(dos);
        }
    }

    public void redirect(DataOutputStream dos, HttpResponse httpResponse) {
        if(httpResponse.getStatusLine().equals(StatusLine.Found) ||
                httpResponse.getStatusLine().equals(StatusLine.SeeOther) ||
                httpResponse.getStatusLine().equals(StatusLine.TemporaryRedirect) ||
                httpResponse.getStatusLine().equals(StatusLine.CustomLogin)
        ){
            logger.debug("httpResponse : {}{}{}{}{}",
                    httpResponse.getHeader(),
                    lineSeparate,httpResponse.getCookie(),
                    lineSeparate,httpResponse.getBody());
            httpResponse.respondRedirect(dos);
        }
    }
}
