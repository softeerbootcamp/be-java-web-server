package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.Controller;
import webserver.controller.ControllerMapper;
import webserver.controller.SignUpController;
import webserver.httpUtils.Request;
import webserver.httpUtils.RequestParser;
import webserver.httpUtils.Response;
import webserver.httpUtils.ResponseHandler;

import java.io.*;
import java.net.Socket;


public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Request req;
    private Response res;
    private static RequestParser reqParser = new RequestParser();
    private static ResponseHandler resHandler = new ResponseHandler();
    private static ControllerMapper controllerMapper = ControllerMapper.getInstance();

    private Socket connection;


    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            req = reqParser.parseRequestFromInputStream(in);
            res = new Response();
            String reqQuery = req.getReqLine().get(Request.QUERY);

            //resHandler = new ResponseHandler(res);
            Controller controller = controllerMapper.mapController(req);
            controller.exec(req, res);
//            // reqLine을 통해 어떤 resLine을 만들지 추론
//            resHandler.probeResLine(req.getReqLine());
//
//            resHandler.sendResponse(out, req.getReqLine().get(Request.QUERY));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
