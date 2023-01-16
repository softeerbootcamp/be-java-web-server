package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.httpUtils.Request;
import webserver.httpUtils.RequestParser;
import webserver.httpUtils.Response;
import webserver.httpUtils.ResponseHandler;

import java.io.*;
import java.net.Socket;


public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Request req;
    private RequestParser reqParser;

    private Socket connection;


    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            reqParser = new RequestParser();
            req = reqParser.parseRequestFromInputStream(in); // parse to reqline, header, body
            String reqQuery = req.getReqLine().get(Request.QUERY);

            ResponseHandler resHandle = new ResponseHandler(new Response());
            if(reqQuery.contains("/create") &&
               req.getReqLine().get(Request.METHOD).equals("GET"))
            {
                // GET 방식의 회원가입 처리
                req.getReqLine()
                        .put(Request.QUERY,
                             SignUpController.enrollNewUser(reqQuery));
            }
            // reqLine을 통해 어떤 resLine을 만들지 추론
            resHandle.probeResLine(req.getReqLine());

            resHandle.sendResponse(out, req.getReqLine().get(Request.QUERY));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
