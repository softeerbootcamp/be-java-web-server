package controller;

import servlet.Servlet;
import servlet.UserCreate;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ServletController {

    private final Map<String, Class<? extends Servlet>> controller;

    public ServletController() {
        this.controller = new HashMap<>();
        controller.put("/user/create", UserCreate.class);
    }

    public Class<? extends Servlet> getServlet(String path) {
        return controller.get(path);
    }

    public Servlet newInstance(Class<? extends Servlet> servletClass)
            throws NoSuchMethodException,
            InvocationTargetException,
            InstantiationException,
            IllegalAccessException {
        Servlet servlet = servletClass.getDeclaredConstructor().newInstance();
        return servlet;
    }
}
