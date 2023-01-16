package webserver;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import controller.Controller;
import controller.StaticController;
import controller.TemplatesController;
import controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpStatus;
import view.RequestBodyMessage;
import view.RequestHeaderMessage;
import view.RequestMessage;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private HttpStatus httpStatus;
    private Socket connection;
    private Controller controller;
    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            httpStatus = HttpStatus.ClientError;
            InputStreamReader reader = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(reader);
            RequestMessage requestMessage = new RequestMessage(new RequestHeaderMessage(br.readLine()), new RequestBodyMessage());
            //RequestHeaderMessage requestHeaderMessage = new RequestHeaderMessage(br.readLine());
            String brStr = "";
            boolean bodyExist = false;
            char[] body = new char[0];
            int body_sz = 0;
            while(!(brStr=br.readLine()).equals("")){
                int idx = -1;
                if (brStr.contains("Content-Length")){
                    body_sz = Integer.parseInt(brStr.substring("Content-Length: ".length()));
                    body = new char[body_sz];
                }
                logger.debug(brStr);
            }
            br.read(body);
            logger.debug(new String(body));
            setController(requestMessage.getRequestHeaderMessage(), out);
            logger.debug(controller.toString());
            controller.control();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void setController(RequestHeaderMessage requestHeaderMessage, OutputStream out){
        if (requestHeaderMessage.getContentType().contains("html")){
            controller = new TemplatesController(requestHeaderMessage, out);
            return;
        }
            if (requestHeaderMessage.getHttpOnlyURL().contains(".")) {
            controller = new StaticController(requestHeaderMessage, out);
            return;
        }
        if (requestHeaderMessage.getHttpOnlyURL().startsWith("/user")) {
            controller = new UserController(requestHeaderMessage, out);
            return;
        }
    }

}
