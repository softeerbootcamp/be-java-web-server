package webserver.controller;

import org.checkerframework.checker.units.qual.C;
import webserver.httpUtils.Request;
import webserver.httpUtils.RequestParser;

import java.util.Map;
public class ControllerMapper {
    private static ControllerMapper instance;
    private Map<String, Controller> controllerMap;

    public static final String SIGNUP = "SignUp";
    public static final String STATIC = "Static";

    private ControllerMapper()
    {
        controllerMap.put(SIGNUP, new SignUpController());
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

    public Controller mapController(Request req)
    {
        Map<String, String> reqLine = req.getReqLine();
        String query = reqLine.get(Request.QUERY);
        
        if(query.contains("create"))
        {
            return new SignUpController();
        }
        return new StaticFileController();
    }
}
