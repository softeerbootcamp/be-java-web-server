package webserver;

import java.io.*;
import java.net.Socket;
import java.util.Optional;

import com.sun.net.httpserver.HttpPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.Controller.AuthController;
import webserver.Controller.Controller;
import webserver.domain.StatusCodes;
import webserver.domain.request.Request;
import webserver.domain.response.Response;
import webserver.exception.HttpRequestException;
import webserver.utils.HttpRequestUtils;
import webserver.utils.HttpResponseUtil;
import webserver.utils.StaticControllerUtil;

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
            StaticControllerUtil.staticFileResolver(requestedPath).ifPresentOrElse(fileAsBytes ->{
                res.makeResponse(fileAsBytes, StatusCodes.OK.getStatusCode(), StatusCodes.OK.getStatusMsg());
            },()->{
                try{
                    Controller controller = handlerMapping.getHandler(requestedPath);
                    controller.handle(req.getRequestLine().getResource(), res);
                }catch (HttpRequestException e){
                 res.makeResponse(e.getErrorMsg().getBytes(), e.getErrorCode(), e.getErrorMsg());
                }
            });
        } catch (IOException e) {
            logger.debug(e.getMessage());
        }
    }

}
