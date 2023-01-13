package webserver;

import java.io.*;
import java.net.Socket;

import controller.Controller;
import controller.ControllerSelector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.Request;
import response.Response;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            Response response = new Response(out);
            BufferedReader br = new BufferedReader(new InputStreamReader(in,"UTF-8"));
            Request request = new Request(br);
//
            Controller controller = ControllerSelector.setController(request.getRequestLine());
            controller.selectedController(request,response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


}
