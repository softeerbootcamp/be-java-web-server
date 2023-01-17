package controller;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import controller.annotation.ControllerInfo;
import controller.annotation.ControllerMethodInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import util.Url;
import webserver.RequestHandler;

import java.io.DataOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.NoSuchFileException;
import java.util.Arrays;
import java.util.Optional;

public class ControllerFinder {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);


    public static Controller findController(Url url) {
        try {
            String packageName = "controller";
            ClassPath classpath = ClassPath.from(Thread.currentThread().getContextClassLoader());
            ImmutableSet<ClassPath.ClassInfo> havingControllerInfoClasses = classpath.getTopLevelClassesRecursive(packageName);
            for (ClassPath.ClassInfo classInfo : havingControllerInfoClasses) {
                Class<Controller> clazz = (Class<Controller>) Class.forName(classInfo.getName());
                if (clazz.isAnnotationPresent(ControllerInfo.class)) {
                    ControllerInfo controllerInfo = clazz.getAnnotation(ControllerInfo.class);
                    if (url.getUrl().matches(controllerInfo.regex())) {
                        return clazz.newInstance();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new FileController();
    }

    public static void handleControllerInfoAnnotation(Controller controller, HttpRequest httpRequest, DataOutputStream dataOutputStream) throws IllegalAccessException, NoSuchFileException, InvocationTargetException {
        Method[] methods = controller.getClass().getMethods();
        Optional<Method> matchedMethod = Arrays.stream(methods)
                .filter(m -> m.isAnnotationPresent(ControllerMethodInfo.class))
                .filter(m -> isMatchControllerMethodWithRequest(m.getAnnotation(ControllerMethodInfo.class), httpRequest))
                .findAny();

        if (matchedMethod.isPresent()) {
            matchedMethod.get().invoke(controller, dataOutputStream, httpRequest);
        } else {
            throw new InvocationTargetException(new Exception("Method not found"), "method not found");
        }

    }

    private static boolean isMatchControllerMethodWithRequest(ControllerMethodInfo controllerMethodInfo, HttpRequest httpRequest) {
        return httpRequest.getUrl().getRequestDataType().equals(controllerMethodInfo.type())
                && httpRequest.getUrl().getUrl().contains(controllerMethodInfo.path())
                && httpRequest.getHttpMethod().equals(controllerMethodInfo.method());
    }


}

