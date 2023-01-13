package webserver;

import webserver.domain.StatusCodes;
import webserver.domain.response.Response;
import webserver.exception.HttpRequestException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ControllerExecutioner {

    public static void executeController(Class<?> clazz, Map<String, String> queryString, Response res, String path){
        List<Method> methodList = Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(ControllerInfo.class))
                .collect(Collectors.toList());
        methodList.forEach(method -> {
            ControllerInfo controllerInfo = method.getAnnotation(ControllerInfo.class);
            if(controllerInfo.path().startsWith(path)){
                try {
                    ArgumentResolver.checkParameters(queryString, Arrays.asList(controllerInfo.queryStr()));
                    method.invoke(clazz.newInstance(), queryString, res);
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new HttpRequestException(StatusCodes.INTERNAL_SERVER_ERROR);
                } catch (InvocationTargetException e) {
                    throw new HttpRequestException(StatusCodes.INTERNAL_SERVER_ERROR);
                }
            }
        });
    }
}
