package webserver;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import controller.ServletController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.Header;
import request.HttpRequest;
import response.HttpResponse;
import response.ResponseHandler;
import servlet.Servlet;
import util.FileIoUtils;
import util.PathUtils;


public class RequestHandler implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = generateHttpRequest(in);
            HttpResponse httpResponse = ResponseHandler.controlRequestAndResponse(httpRequest);
            respondToHttpRequest(out, httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private HttpRequest generateHttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        return HttpRequest.of(br);
    }



    private void respondToHttpRequest(OutputStream out, HttpResponse httpResponse) {
        DataOutputStream dos = new DataOutputStream(out);
        httpResponse.respond(dos);
    }

}
