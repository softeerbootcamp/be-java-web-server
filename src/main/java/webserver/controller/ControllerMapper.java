package webserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.httpUtils.Request;

import java.util.HashMap;
import java.util.Map;
public class ControllerMapper {
    private static final Logger logger = LoggerFactory.getLogger(ControllerMapper.class);
    private static ControllerMapper instance;
    private Map<String, Controller> controllerMap;

    public static final String DYNAMIC = "Dynamic";
    public static final String STATIC = "Static";
    public static final String HOMEPATH = "Home";

    private ControllerMapper()
    {
        controllerMap = new HashMap<>();
        controllerMap.put(STATIC, new StaticFileController());
        controllerMap.put(DYNAMIC, new DynamicFileController());
        controllerMap.put(HOMEPATH, new HomePathController());
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
        String reqQuery = req.getReqLine().getQuery();
        if(reqQuery.endsWith("index.html"))
        {
            logger.debug("current controller : home");
            return controllerMap.get(HOMEPATH);
        }
        if(req.hasCookie() && (reqQuery.endsWith(".html") || reqQuery.endsWith("user/create/article")))
        {
            logger.debug("current controller : dynamic");
            return controllerMap.get(DYNAMIC);
        }
        logger.debug("current controller : static");
        return controllerMap.get(STATIC);
    }
}
