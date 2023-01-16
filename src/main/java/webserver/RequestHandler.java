package webserver;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.nio.file.NoSuchFileException;

import controller.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import response.HttpResponse;
import util.HttpStatus;
import util.UrlType;

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

            Controller controller = ControllerFinder.factoryController(httpRequest.getUrl());


            try {
                ControllerFinder.handleControllerInfoAnnotation(controller, httpRequest, clientOutPutStream);
            }
            catch (InvocationTargetException e) {
                ErrorController.get404ErrorResponse(clientOutPutStream);
                logger.error("[ERROR}:{} {}", HttpStatus.NOT_FOUND.getCode(),HttpStatus.NOT_FOUND.getMessage());
                logger.error("FileReaderContoller에서 url에 맞는 페이지가 없습니다. url:{}",httpRequest.getUrl().getUrl());
            }
            catch (Exception e) {
                e.printStackTrace();
                logger.error("[ERROR]:{} {}", HttpStatus.INTERNAL_SERVER_ERROR.getCode(),HttpStatus.INTERNAL_SERVER_ERROR.getMessage());
                logger.error("요청에 맞지 않은 요청이 들어옴. controller:{}, url:{}",controller.getClass(),httpRequest.getUrl().getUrl());
                ErrorController.get500ErrorResponse(clientOutPutStream);

            }

//            controller = Controller.FactoryController(httpRequest.getUrl());
//            HttpResponse httpResponse;
//            UrlType urlType = UrlType.getUrlType(httpRequest.getUrl().getUrl());
//            try {
//                if (controller instanceof FileController) {
//                    if (urlType.equals(UrlType.TEMPLATES_FILE)) {
//                        httpResponse = ((FileController) controller).TemplateController(clientOutPutStream, httpRequest);
//                    } else if (urlType.equals(UrlType.STATIC_FILE)) {
//                        httpResponse = ((FileController) controller).StaticController(clientOutPutStream, httpRequest);
//                    }
//                } else if (controller instanceof UserController) {
//                    httpResponse = ((UserController) controller).UserQueryString(clientOutPutStream, httpRequest);
//                }
//                else{
//                    httpResponse = ((ErrorController) controller).getErrorResponse(clientOutPutStream);
//                }
//            } catch (Exception e) {
//                controller = new ErrorController();
//                logger.error("해당 url에 맞는 controller 가 없습니다. url: {}", httpRequest.getUrl().getUrl());
//                httpResponse = ((ErrorController) controller).getErrorResponse(clientOutPutStream);
//            }


        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


}


