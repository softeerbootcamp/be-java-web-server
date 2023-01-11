package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.httpUtils.RequestParser;
import webserver.httpUtils.Response;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Map;

import static webserver.Paths.TEMPLATE_PATH;
import static webserver.Paths.STATIC_PATH;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Map<String, String> reqLine;
    private String reqHeader;
    private String reqBody;

    private Socket connection;


    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            reqLine = RequestParser.parseRequestLine(in);
            String reqMethod = reqLine.get(RequestParser.METHOD);
            String reqQuery = reqLine.get(RequestParser.QUERY);
            String reqVersion = reqLine.get(RequestParser.VERSION);

            Response res = new Response(logger);
            if(reqQuery.contains("/create") && reqMethod.equals("GET"))
            {
                // GET 방식의 회원가입 처리
                SignUpController.enrollNewUser(reqQuery);
            }
            // reqLine을 통해 어떤 resLine을 만들지 추론
            res.probeResLine(reqMethod, reqQuery, reqVersion);

            res.sendResponse(out, reqQuery);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
