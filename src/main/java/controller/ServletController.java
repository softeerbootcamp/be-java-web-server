package controller;

import servlet.Servlet;
import servlet.UserCreate;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ServletController {

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
        return servletInstance;
    }
}
