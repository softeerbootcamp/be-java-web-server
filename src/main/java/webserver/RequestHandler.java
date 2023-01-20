package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;

import controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import request.HttpRequestMapper;
import response.HttpResponse;
import response.StatusCode;

import javax.naming.AuthenticationException;

public class RequestHandler implements Runnable{

    private static final String lineSeparate = System.lineSeparator();
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final Socket connection; // Client
    private final HttpRequestMapper httpRequestMapper;

    public RequestHandler(Socket connectionSocket, HttpRequestMapper httpRequestMapper) {
        this.connection = connectionSocket;
        this.httpRequestMapper = httpRequestMapper;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());


        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            /* TODO
            *   - in : 서버 -> 클라이언트로 응답을 보내는 데이터를 싣음 */
            HttpResponse httpResponse = controlRequestAndResponse(HttpRequest.of(in));
            respondToHttpRequest(out, httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public HttpResponse controlRequestAndResponse(HttpRequest httpRequest) {
        /*
        * HttpRequestMapper의 getController를 통해
        * */
        try{
            Controller controller = httpRequestMapper.getController(httpRequest);
            return controller.service(httpRequest);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("control Req/Res error");
    }

    private void respondToHttpRequest(OutputStream out, HttpResponse httpResponse) {
        DataOutputStream dos = new DataOutputStream(out);
        httpResponse.respond(dos);
    }
}
