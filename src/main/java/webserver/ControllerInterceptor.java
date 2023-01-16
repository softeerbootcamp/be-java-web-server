package webserver;

import webserver.annotation.ControllerInfo;
import webserver.domain.RequestMethod;
import webserver.domain.StatusCodes;
import webserver.domain.request.Request;
import webserver.domain.response.Response;
import webserver.exception.HttpRequestException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ControllerInterceptor {

    private static List<Method> extractAllMethods (Class<?> clazz){
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(ControllerInfo.class))
                .collect(Collectors.toList());
    }

    public static void executeController(Class<?> clazz, Request req, Response res){

        Map<String, String> queryString = req.getRequestLine().getResource().getQueryString();
        String path = req.getRequestLine().getResource().getPath();
        RequestMethod requestMethod = req.getRequestLine().getRequestMethod();
        Map<String, String> reqBody;

        //extract all the methods in the class which include specific annotation
        List<Method> methodList = extractAllMethods(clazz);

        methodList.forEach(method -> {
            ControllerInfo controllerInfo = method.getAnnotation(ControllerInfo.class);
            if(controllerInfo.path().startsWith(path)){
                try {
                    if(controllerInfo.method() != requestMethod)
                        throw new HttpRequestException(StatusCodes.METHOD_NOT_ALLOWED);
                    ArgumentResolver.checkParameters(queryString, Arrays.asList(controllerInfo.queryStr()));  //check the validity of parameters before executing the method
                    method.invoke(clazz.newInstance(), queryString, res);    //invoke method
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new HttpRequestException(StatusCodes.INTERNAL_SERVER_ERROR);
                } catch (InvocationTargetException e) {
                    throw new HttpRequestException(StatusCodes.INTERNAL_SERVER_ERROR);
                }
            }
        });
    }
}
