package webserver;

import java.io.*;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.Controller.AuthController;
import webserver.Controller.Controller;
import webserver.domain.ContentType;
import webserver.domain.StatusCodes;
import webserver.domain.request.Request;
import webserver.domain.response.Response;
import webserver.exception.HttpRequestException;
import webserver.utils.CommonUtils;
import webserver.utils.StaticResourceFinder;

import static webserver.utils.HttpRequestUtils.parseHttpRequest;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private AuthController authController;
    private Socket connection;
    private HandlerMapping handlerMapping;

    public RequestHandler(Socket connectionSocket) {

        this.connection = connectionSocket;
        this.authController = new AuthController();
        this.handlerMapping = new HandlerMapping();

    }


    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            Request req = parseHttpRequest(in);
            Response res = new Response(out);

            req.readRequest();  //Print out Http Request Message

            String requestedPath = req.getRequestLine().getResource();
            StaticResourceFinder.staticFileResolver(requestedPath).ifPresentOrElse(fileAsBytes ->{
                res.setBody(fileAsBytes);
                res.setContentType(StaticResourceFinder.getExtension(requestedPath));
                res.setStatusCode(StatusCodes.OK);
                res.writeResponse();
            },()->{
                try{
                    Controller controller = handlerMapping.getHandler(requestedPath);
                    String [] parsedPath = CommonUtils.parseRequestLine(requestedPath);
                    controller.handle(parsedPath[0], parsedPath[1], res);
                    res.writeResponse();
                }catch (HttpRequestException e){
                    res.setStatusCode(e.getErrorCode());
                    res.writeResponse();
                }
            });
        } catch (IOException e) {
            logger.debug(e.getMessage());
        }
    }

}
