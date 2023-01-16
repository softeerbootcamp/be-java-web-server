package webserver;

import java.io.*;
import java.net.Socket;

import controller.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import response.HttpResponse;
import util.UrlType;

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
            HttpRequest httpRequest = HttpRequest.getHttpRequest(in);

            DataOutputStream clientOutPutStream = new DataOutputStream(out);

//TODO ControllerHandler 구현 진행중 ( 아래의 코드를 삭제후 주석코드로 리팩토링 진행중)
//            ControllerHandlerImp controller1 = ControllerHandlerImp.findController(httpRequest.getUrl());
//            try{
//                controller1.handle(httpRequest.getUrl(), clientOutPutStream, httpRequest);
//            } catch (Exception e) {
//                controller = new ErrorController();
//                logger.error("해당 url에 맞는 controller 가 없습니다. url: {}", httpRequest.getUrl().getUrl());
//                ((ErrorController) controller).getErrorResponse(clientOutPutStream);
//            }

            controller = Controller.FactoryController(httpRequest.getUrl());
            HttpResponse httpResponse;
            UrlType urlType = UrlType.getUrlType(httpRequest.getUrl().getUrl());
            try {
                if (controller instanceof FileController) {
                    if (urlType.equals(UrlType.TEMPLATES_FILE)) {
                        httpResponse = ((FileController) controller).TemplateController(clientOutPutStream, httpRequest);
                    } else if (urlType.equals(UrlType.STATIC_FILE)) {
                        httpResponse = ((FileController) controller).StaticController(clientOutPutStream, httpRequest);
                    }
                } else if (controller instanceof UserController) {
                    httpResponse = ((UserController) controller).UserQueryString(clientOutPutStream, httpRequest);
                }
                else{
                    httpResponse = ((ErrorController) controller).getErrorResponse(clientOutPutStream);
                }
            } catch (Exception e) {
                controller = new ErrorController();
                logger.error("해당 url에 맞는 controller 가 없습니다. url: {}", httpRequest.getUrl().getUrl());
                httpResponse = ((ErrorController) controller).getErrorResponse(clientOutPutStream);
            }


        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


}


