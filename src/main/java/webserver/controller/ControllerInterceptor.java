package webserver.controller;

import webserver.ArgumentResolver;
import webserver.annotation.ControllerInfo;
import webserver.domain.ContentType;
import webserver.domain.ModelAndView;
import webserver.domain.RequestMethod;
import webserver.domain.StatusCodes;
import webserver.domain.request.Request;
import webserver.domain.response.Response;
import webserver.exception.HttpRequestException;
import webserver.security.SecurityFilter;
import webserver.utils.HttpSessionUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ControllerInterceptor {

    private static List<Method> extractAllMethods (Class<?> clazz){   //get all the methods in a specific class
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(ControllerInfo.class))
                .collect(Collectors.toList());
    }

    private static Map<String, String> checkMethodThenReturnBody(Request req, ControllerInfo controllerInfo, RequestMethod requestMethod) throws HttpRequestException {
        if (requestMethod != controllerInfo.method())
            throw new HttpRequestException(StatusCodes.METHOD_NOT_ALLOWED, "<script>alert('올바르지 않은 요청입니다'); history.go(-1);</script>");
        if (requestMethod == RequestMethod.GET)
            return req.getRequestLine().getResource().getQueryString();
        return req.getBody();
    }

    private static void loginSessionCheck(Request req, Map<String, String> contentMap, ModelAndView mv){
        String sessionId = HttpSessionUtils.getSessionIdFromRequest(req).orElse(null);
        if (HttpSessionUtils.isSessionValid(sessionId)) {
            contentMap.put("Cookie", sessionId);
            mv.addViewModel("login", true);
        }else{
            mv.addViewModel("login", false);
        }
        mv.addViewModel("session-id", sessionId);
    }


    public static void executeController(Controller controller, Request req, Response res, ModelAndView mv) throws HttpRequestException{
        //extract all the methods in the class which include specific annotation
        Class<? extends Controller> clazz = controller.getClass();
        List<Method> methodList = extractAllMethods(clazz);
        Map<String, String> contentMap = new HashMap<>();

        String reqPath = req.getRequestLine().getResource().getPath();
        mv.setViewPath(reqPath);

        //hand over the session to controller only if it is valid
        loginSessionCheck(req, contentMap, mv);
        SecurityFilter.checkAuthorization(reqPath, mv);

        methodList.forEach(method -> {
            ControllerInfo controllerInfo = method.getAnnotation(ControllerInfo.class);
            if(controllerInfo.path().equals(reqPath)) {
                try {
                    //check the validity of http method & parameters before executing the method
                    contentMap.putAll(checkMethodThenReturnBody(req, controllerInfo, req.getRequestLine().getRequestMethod()));
                    ArgumentResolver.checkParameters(contentMap, Arrays.asList(controllerInfo.queryStr()));
                    //invoke method
                    method.invoke(controller, contentMap, res, mv);
                } catch (HttpRequestException e){
                    res.error(e.getErrorCode(), e.getMsg().getBytes(), ContentType.TEXT_HTML);
                } catch (IllegalAccessException | InvocationTargetException e) {
                        throw (HttpRequestException) e.getCause();
                }
            }
        });
    }
}
