package webserver;

import java.io.*;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.Controller;
import webserver.domain.ContentType;
import webserver.view.ModelAndView;
import webserver.domain.request.Request;
import webserver.domain.response.Response;
import webserver.exception.HttpRequestException;
import webserver.utils.HttpRequestUtils;
import webserver.utils.HttpResponseWriter;
import webserver.view.View;
import webserver.view.ViewResolver;

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

            Request req = HttpRequestUtils.parseHttpRequest(in);
            Response res = new Response();
            ModelAndView mv = new ModelAndView();

            try{
                Controller controller = handlerMapping.getHandler(req);  //get the controller to handle request
                controller.chain(req, res, mv);
                View targetView = ViewResolver.getHandler(mv);
                targetView.makeView(req, res, mv);
            }catch (HttpRequestException e){
                res.error(e.getErrorCode(), e.getMsg().getBytes(), ContentType.TEXT_HTML);
            }
            HttpResponseWriter.of(res, out); //write http response and send it back to client side

        } catch (IOException e) {
            logger.debug(e.getMessage());
        }

    }
}
