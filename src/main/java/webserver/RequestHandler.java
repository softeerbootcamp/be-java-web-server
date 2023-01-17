package webserver;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.nio.file.NoSuchFileException;

import controller.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import util.HttpStatus;

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
            HttpRequest httpRequest = HttpRequest.getHttpRequest(in);

            DataOutputStream clientOutPutStream = new DataOutputStream(out);

            Controller controller = ControllerFinder.findController(httpRequest.getUrl());


            try {
                ControllerFinder.handleControllerInfoAnnotation(controller, httpRequest, clientOutPutStream);
            } catch (NoSuchFileException e) {
                e.printStackTrace();
                ErrorController.getErrorResponse(clientOutPutStream,HttpStatus.NOT_FOUND);
                logger.error("[ERROR]:{} {}", HttpStatus.NOT_FOUND.getCode(), HttpStatus.NOT_FOUND.getMessage());
                logger.error("FileReaderContoller에서 url에 맞는 페이지가 없습니다. url:{}", httpRequest.getUrl().getUrl());
            } catch(InvocationTargetException e){
                e.printStackTrace();
                ErrorController.getErrorResponse(clientOutPutStream,HttpStatus.METHOD_NOT_ALLOWED);
                logger.error("[ERROR]:{} {}", HttpStatus.NOT_FOUND.getCode(), HttpStatus.METHOD_NOT_ALLOWED.getMessage());
                logger.error("url요청이 올바르지 않습니다! url:{}", httpRequest.getUrl().getUrl());
            }
            catch (Exception e) {
                e.printStackTrace();
                logger.error("[ERROR]:{} {}", HttpStatus.INTERNAL_SERVER_ERROR.getCode(), HttpStatus.INTERNAL_SERVER_ERROR.getMessage());
                logger.error("서버에서 처리못하는 에러. controller:{}, url:{}", controller.getClass(), httpRequest.getUrl().getUrl());
                ErrorController.getErrorResponse(clientOutPutStream,HttpStatus.INTERNAL_SERVER_ERROR);

            }

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


}


