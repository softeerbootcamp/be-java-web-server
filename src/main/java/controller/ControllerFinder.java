package controller;

import controller.annotation.ControllerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import util.FileType;
import util.Url;
import webserver.RequestHandler;

import java.io.DataOutputStream;
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
                if(isMatchControllerMethodWithRequest(controllerInfo,httpRequest)) {
                    method.invoke(controller,dataOutputStream,httpRequest);
                    logger.debug("호출된 Url: {}",httpRequest.getUrl().getUrl());
                    logger.debug("호출된 Controller: {}",controller.getClass());
                    logger.debug("호출된 controller-method: {}",method);
                }
            }
        }
    }

    private static boolean isMatchControllerMethodWithRequest(ControllerInfo controllerInfo, HttpRequest httpRequest) {
        return httpRequest.getUrl().getUrlType().equals(controllerInfo.u())
                && httpRequest.getUrl().getUrl().contains(controllerInfo.path())
                && httpRequest.getHttpMethod().equals(controllerInfo.method());
    }


}

