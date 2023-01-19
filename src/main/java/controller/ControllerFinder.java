package controller;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import controller.annotation.Auth;
import controller.annotation.ControllerInfo;
import controller.annotation.ControllerMethodInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import service.SessionService;
import util.error.HttpsErrorMessage;
import util.error.erroclass.FailLoggedException;
import util.error.erroclass.NotLoggedException;
import webserver.RequestHandler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.NoSuchFileException;
import java.util.Arrays;
import java.util.Optional;

public class ControllerFinder {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static SessionService sessionService = new SessionService();


    public static void findController(HttpRequest httpRequest, DataOutputStream dataOutputStream) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, FailLoggedException, InvocationTargetException, NoSuchMethodException, NotLoggedException {
        String packageName = "controller";
        ClassPath classpath = ClassPath.from(Thread.currentThread().getContextClassLoader());
        ImmutableSet<ClassPath.ClassInfo> havingControllerInfoClasses = classpath.getTopLevelClassesRecursive(packageName);
        for (ClassPath.ClassInfo classInfo : havingControllerInfoClasses) {
            Class<Controller> clazz = (Class<Controller>) Class.forName(classInfo.getName());
            if (clazz.isAnnotationPresent(ControllerInfo.class)) {
                handleControllerInfoAnnotation(clazz.newInstance(), httpRequest, dataOutputStream);
            }
        }

    }

    public static void handleControllerInfoAnnotation(Controller controller, HttpRequest httpRequest, DataOutputStream dataOutputStream) throws IllegalAccessException, InvocationTargetException, NotLoggedException {
        Method[] methods = controller.getClass().getMethods();

        Optional<Method> method = Arrays.stream(methods)
                .filter(m -> m.isAnnotationPresent(ControllerMethodInfo.class))
                .filter(m -> isMatchControllerMethodWithRequest(m.getAnnotation(ControllerMethodInfo.class), httpRequest))
                .findAny();

        if (method.isEmpty()) {
            return;
        }

        if (method.get().isAnnotationPresent(Auth.class) && !sessionService.cookieValidInSession(httpRequest.getRequestHeader())) {
            throw new NotLoggedException(HttpsErrorMessage.NOT_LOGGED);
        }
        method.get().invoke(controller, dataOutputStream, httpRequest);
    }


    private static boolean isMatchControllerMethodWithRequest(ControllerMethodInfo controllerMethodInfo, HttpRequest httpRequest) {
        return httpRequest.getUrl().getUrl().matches(controllerMethodInfo.path())
                && httpRequest.getHttpMethod().equals(controllerMethodInfo.method());
    }


}

