package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import servlet.Servlet;
import servlet.UserCreate;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ServletController {

    /*
    *  TODO :
    *   정적 팩토리 메서드에서 멤버 변수를 controller static 으로 구현하는게 어색한데, 왜일까..?
    * */

    private static final Logger logger = LoggerFactory.getLogger(ServletController.class);

    private static Map<String, Class<? extends Servlet>> controller = new HashMap<>();
    private final Class<? extends Servlet> servlet;

    public ServletController(Class<? extends Servlet> servlet) {
        this.controller = new HashMap<>();
        this.servlet = servlet;
    }

    public static ServletController of(String path) {
        controller.put("/user/create", UserCreate.class);

        return new ServletController(controller.get(path));
    }

    public Servlet newInstance()
            throws NoSuchMethodException,
            InvocationTargetException,
            InstantiationException,
            IllegalAccessException {

        Servlet servletInstance = servlet.getDeclaredConstructor().newInstance();
        logger.debug("servlet new Instance : {}",servlet.getName());
        return servletInstance;
    }
}
