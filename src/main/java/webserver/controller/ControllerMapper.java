package webserver.controller;

import webserver.httpUtils.Request;

import java.util.HashMap;
import java.util.Map;
public class ControllerMapper {
    private static ControllerMapper instance;
    private Map<String, Controller> controllerMap;

    public static final String SIGNUP = "SignUp";
    public static final String STATIC = "Static";

    private ControllerMapper()
    {
        controllerMap = new HashMap<>();
        controllerMap.put(SIGNUP, new UserController());
        controllerMap.put(STATIC, new StaticFileController());
    }

    public static ControllerMapper getInstance()
    {
        if(instance == null)
        {
            instance = new ControllerMapper();
        }
        return instance;
    }

    public Controller getController(Request req)
    {
        Map<String, String> reqLine = req.getReqLine();
        String query = reqLine.get(Request.QUERY);

        if(query.contains("user/"))
        {
            return new UserController();
        }
        return new StaticFileController();
    }
}
