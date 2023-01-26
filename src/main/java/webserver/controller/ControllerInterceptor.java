package webserver.controller;

import webserver.argument.argumentResolvers.ArgumentResolver;
import webserver.annotation.ControllerInfo;
import webserver.argument.ArgumentResolverMapping;
import webserver.domain.ContentType;
import webserver.view.ModelAndView;
import webserver.domain.RequestMethod;
import webserver.domain.StatusCodes;
import webserver.domain.request.Request;
import webserver.domain.response.Response;
import webserver.exception.HttpRequestException;
import webserver.security.SecurityContext;
import webserver.security.SecurityFilter;
import webserver.utils.HttpSessionUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ControllerInterceptor {

    private static List<Method> extractAllMethods (Class<?> clazz){   //get all the methods in a specific class
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(ControllerInfo.class))
                .collect(Collectors.toList());
    }

    private static void checkMethodThenReturnBody(ControllerInfo controllerInfo, RequestMethod requestMethod) {
        if (requestMethod != controllerInfo.method())
            throw HttpRequestException.builder()
                    .statusCode(StatusCodes.METHOD_NOT_ALLOWED)
                    .msg("<script>alert('올바르지 않은 요청입니다'); history.go(-1);</script>")
                    .build();
    }

    private static void loginSessionCheck(Request req, ModelAndView mv){
        String sessionId = HttpSessionUtils.getSessionIdFromRequest(req).orElse(null);
        if (HttpSessionUtils.isSessionValid(sessionId)) {
            SecurityContext.addUser(sessionId);
            mv.addViewModel("session-id", sessionId);
        }
    }

    private static Object[] addParamToList(Object[] paramList, Response res, ModelAndView mv) {
        int len = paramList.length;
        paramList = Arrays.copyOf(paramList, len + 2);
        paramList[len]  = res;
        paramList[len+1] = mv;
        return paramList;
    }

    //compare the path of the request with every path of controller method
    private static boolean pathMatcher(String methodPath, String reqPath, ModelAndView mv){
        List<String> methodPathList = Arrays.asList(methodPath.split("/"));
        List<String> reqPathList = Arrays.asList(reqPath.split("/"));
        Map<String, String> pathVarMap = new HashMap<>();

        //TODO : 클래스의 목적과 관련없는 로직은 utils로
        for(int idx = 0 ; idx < reqPathList.size(); idx++){
            String path = methodPathList.get(idx);
            if(Pattern.matches("\\{([^}]*?)\\}", path)){  //in case of @Pathvarible
                pathVarMap.put(path, reqPathList.get(idx));
                continue;
            }
            if(!reqPathList.get(idx).equals(path))
                return false;
        }

        mv.addViewModel("pathVar", pathVarMap);
        return true;
    }

    public static void executeController(Controller controller, Request req, Response res, ModelAndView mv) {
        //extract all the methods in the class which include specific annotation
        Class<? extends Controller> clazz = controller.getClass();
        List<Method> methodList = extractAllMethods(clazz);

        String reqPath = req.getRequestLine().getResource().getPath();
        mv.setViewPath(reqPath);

        loginSessionCheck(req, mv); //hand over the session to controller only if it is valid

        methodList.forEach(method -> {
            ControllerInfo controllerInfo = method.getAnnotation(ControllerInfo.class);
            if(pathMatcher(controllerInfo.path(), reqPath, mv)) {
                try {
                    //check the validity of http method & parameters before executing the method
                    checkMethodThenReturnBody(controllerInfo, req.getRequestLine().getRequestMethod());

                    //authorization checker for chaining
                    SecurityFilter.checkAuthorization(reqPath);

                    //resolve arguments in a method
                    List<Parameter> collect = Arrays.stream(method.getParameters()).filter(parameter -> parameter.getType() != Response.class && parameter.getType() != ModelAndView.class).collect(Collectors.toList());
                    ArgumentResolver argumentResolver = ArgumentResolverMapping.findArgumentResolver(req.getRequestHeader().get("Content-Type"));
                    Object[] paramList = addParamToList(argumentResolver.checkParameters(req, collect, mv), res, mv);

                    //invoke method
                    method.invoke(controller, paramList);

                } catch (HttpRequestException e){
                    res.addHeaderAndBody(e.getErrorCode(), e.getMsg().getBytes(), ContentType.TEXT_HTML);
                } catch (IllegalAccessException | InvocationTargetException | IOException | NoSuchMethodException |
                         InstantiationException ex) {
                    HttpRequestException e = (HttpRequestException) ex.getCause();
                    res.addHeaderAndBody(e.getErrorCode(), e.getMsg().getBytes(), ContentType.TEXT_HTML);
                }
            }
        });
    }

}
