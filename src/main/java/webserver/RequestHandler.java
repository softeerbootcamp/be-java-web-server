package webserver;

import java.io.*;
import java.net.Socket;
import java.util.Map;

import controller.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.RequestBodyMessage;
import view.RequestHeaderMessage;
import view.RequestMessage;
import view.RequestReader;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private Socket connection;
    private Controller controller;
    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            InputStreamReader reader = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(reader);
            RequestReader requestReader = new RequestReader(br);
            String startLine = requestReader.readStartLine();
            Map<String,String> header = requestReader.readHeader();
            char[] body = requestReader.readBody(Integer.parseInt(header.getOrDefault("Content-Length","0")));
            RequestMessage requestMessage = new RequestMessage(new RequestHeaderMessage(startLine, header), new RequestBodyMessage(body));
            setController(requestMessage);
            controller.control(requestMessage, out);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void setController(RequestMessage requestMessage){
        RequestHeaderMessage requestHeaderMessage = requestMessage.getRequestHeaderMessage();
        if (requestHeaderMessage.isLogin()){
            controller = LoginController.getInstance();
            return;
        }
        if (requestHeaderMessage.getHttpOnlyURL().contains(".")) {
            controller = StaticController.getInstance();
            return;
        }
        if (requestHeaderMessage.getHttpOnlyURL().startsWith("/user")) {
            controller = UserController.getInstance();
            return;
        }
    }

}
