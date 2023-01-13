package webserver;

import java.io.*;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.Controller.Controller;
import webserver.domain.StatusCodes;
import webserver.domain.request.Request;
import webserver.domain.response.Response;
import webserver.utils.HttpResponseUtils;
import static webserver.utils.HttpRequestUtils.parseHttpRequest;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private Socket connection;
    private HandlerMapping handlerMapping;

    public RequestHandler(Socket connectionSocket) {

        this.connection = connectionSocket;
        this.handlerMapping = new HandlerMapping();

    }



    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            Request req = parseHttpRequest(in);
            Response res = new Response();

            handlerMapping.getStaticHandler().handle(req, res);  //get static controller to handle this request
            if(res.getStatusCode() == StatusCodes.NOT_FOUND){  // request is not precessed by static controller
                Controller controller = handlerMapping.getHandler(req, res);  //get the controller to handle request
                controller.handle(req, res);
            }

            HttpResponseUtils responseWriter = new HttpResponseUtils(res, out);
            responseWriter.makeResponse();  //write http response and send it back to client side

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
