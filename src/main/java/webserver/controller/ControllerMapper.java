package webserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.httpUtils.Request;
import webserver.httpUtils.RequestGetter;
import webserver.httpUtils.entity.ReqLine;

import java.util.HashMap;
import java.util.Map;
public class ControllerMapper {
    private static final Logger logger = LoggerFactory.getLogger(ControllerMapper.class);
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
        if(req.hasCookie() && req.getReqLine().getQuery().endsWith(".html"))
        {
            logger.debug("current controller : dynamic");
            return new DynamicFileController();
        }
        logger.debug("current controller : static");
        return new StaticFileController();
    }
}
