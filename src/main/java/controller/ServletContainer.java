package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ServletContainer {

    /*
    *  TODO :
    *   정적 팩토리 메서드에서 멤버 변수를 controller static 으로 구현하는게 어색한데, 왜일까..?
    * */

    private static final Logger logger = LoggerFactory.getLogger(ServletContainer.class);
    private final Map<String, String> servletNameMapper;
    private final Map<String, Controller> servletContainer;

    public ServletContainer() {
        servletNameMapper = new HashMap<>();
        servletContainer = new HashMap<>();
        servletNameMapper.put("/user/create", "controller.UserCreateController");
        servletNameMapper.put("/user/login", "controller.LoginController");
        servletNameMapper.put("/user/list", "controller.UserLoginListController");
    }

    public boolean hasMappingServlet(HttpRequest httpRequest) {
        return servletNameMapper.containsKey(httpRequest.getDefaultPath());
    }

    public Controller getController(HttpRequest httpRequest) {
        return getInstance(httpRequest.getDefaultPath());
    }

    private synchronized Controller getInstance(String requestPath) {
        try {
            if (servletContainer.get(requestPath) == null) {
                String className = servletNameMapper.get(requestPath);
                Class clazz = Class.forName(className);
                servletContainer.put(requestPath, (Controller)clazz.getDeclaredConstructor().newInstance());
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NullPointerException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(requestPath);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return servletContainer.get(requestPath);
    }
}
