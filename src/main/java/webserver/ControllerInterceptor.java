package webserver;

import model.HttpCookie;
import webserver.annotation.ControllerInfo;
import webserver.domain.ContentType;
import webserver.domain.RequestMethod;
import webserver.domain.StatusCodes;
import webserver.domain.request.Request;
import webserver.domain.response.Response;
import webserver.exception.HttpRequestException;
import webserver.security.SecurityFilter;
import webserver.utils.HttpCookieUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ControllerInterceptor {

    private static List<Method> extractAllMethods (Class<?> clazz){   //get all the mathods in a specific class
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(ControllerInfo.class))
                .collect(Collectors.toList());
    }

    private static Map<String, String> checkMethodThenReturnBody(Request req, ControllerInfo controllerInfo, RequestMethod requestMethod) throws HttpRequestException {
        if (requestMethod != controllerInfo.method())
            throw new HttpRequestException(StatusCodes.METHOD_NOT_ALLOWED);
        if (requestMethod == RequestMethod.GET)
            return req.getRequestLine().getResource().getQueryString();
        return req.getBody();
    }

    public static void executeController(Class<?> clazz, Request req, Response res) throws HttpRequestException{
        //extract all the methods in the class which include specific annotation
        List<Method> methodList = extractAllMethods(clazz);

        methodList.forEach(method -> {
            ControllerInfo controllerInfo = method.getAnnotation(ControllerInfo.class);
            String reqPath = req.getRequestLine().getResource().getPath();
            if(controllerInfo.path().startsWith(reqPath)) {
                try {
                    //check the validity of http method & parameters before executing the method
                    Map<String, String> contentMap = checkMethodThenReturnBody(req, controllerInfo, req.getRequestLine().getRequestMethod());
                    ArgumentResolver.checkParameters(contentMap, Arrays.asList(controllerInfo.queryStr()));

                    //hand over the session to controller only if it is valid
                    //TODO : 해당 사항을 별도의 클래스로 분리해서 Security 관련 기능을 다룰 것

                    String sessionId = HttpCookieUtils.getSessionIdFromRequest(req).orElse(null);
                    SecurityFilter.checkAuthorization(reqPath, sessionId);
                    if (sessionId != null){
                        HttpCookieUtils.cookieValidation(sessionId).ifPresent(cookie -> {
                            contentMap.put("Cookie", cookie.getSessionId());
                        });
                    }

                    //invoke method
                    method.invoke(clazz.newInstance(), contentMap, res);

                } catch (HttpRequestException e){
                    res.error(e.getErrorCode(), e.getMsg().getBytes(), ContentType.TEXT_HTML);
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new HttpRequestException(StatusCodes.INTERNAL_SERVER_ERROR);
                } catch (InvocationTargetException e) {
                    throw new HttpRequestException(StatusCodes.INTERNAL_SERVER_ERROR);
                }
            }
        });
    }

}
