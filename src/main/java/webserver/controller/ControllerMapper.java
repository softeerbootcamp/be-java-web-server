package webserver.controller;

import webserver.httpUtils.Request;
import webserver.httpUtils.entity.ReqLine;

import java.util.HashMap;
import java.util.Map;
public class ControllerMapper {
    private static ControllerMapper instance;
    private Map<String, Controller> controllerMap;

    public static final String DYNAMIC = "Dynamic";
    public static final String STATIC = "Static";

    private ControllerMapper()
    {
        controllerMap = new HashMap<>();
        controllerMap.put(STATIC, new StaticFileController());
        controllerMap.put(DYNAMIC, new DynamicFileController());
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
        ReqLine reqLine = req.getReqLine();
        String query = reqLine.getQuery();

        return new StaticFileController();
    }
}
