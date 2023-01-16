package controller;

import controller.annotation.ControllerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import response.HttpResponse;
import util.FileType;
import util.HttpMethod;
import util.Url;
import util.UrlType;
import webserver.RequestHandler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.NoSuchFileException;

public class ControllerFinder {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public static Controller factoryController(Url url) {
        FileType fileType = FileType.getFileType(url);
        if (fileType != null) {
            return new FileController();
        } else if(url.getUrl().matches(UserController.REGEX)){
            return new UserController();
        }else{
            return new ErrorController();
        }
    }

    public static void handleControllerInfoAnnotation(Controller controller, HttpRequest httpRequest, DataOutputStream dataOutputStream) throws InvocationTargetException, IllegalAccessException, NoSuchFileException {
        Method[] methods = controller.getClass().getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(ControllerInfo.class)) {
                ControllerInfo controllerInfo = method.getAnnotation(ControllerInfo.class);
                String controllersPath = controllerInfo.path();
                UrlType controllersUrlType = controllerInfo.u();
                HttpMethod controllerHttpMethod = controllerInfo.method();
                if (httpRequest.getUrl().getUrlType().equals(controllersUrlType)
                        && httpRequest.getUrl().getUrl().contains(controllersPath)
                        && httpRequest.getHttpMethod().equals(controllerHttpMethod)) {
                    method.invoke(controller,dataOutputStream,httpRequest);
                    logger.debug("호출된 Url: {}",httpRequest.getUrl().getUrl());
                    logger.debug("호출된 Controller: {}",controller.getClass());
                    logger.debug("호출된 controller-method: {}",method);
                }
            }
        }
    }


}

