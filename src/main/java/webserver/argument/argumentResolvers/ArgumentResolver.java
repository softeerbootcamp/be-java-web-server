package webserver.argument.argumentResolvers;

import webserver.annotation.PathVariable;
import webserver.argument.argumentResolveStrategies.*;
import webserver.domain.RequestMethod;
import webserver.domain.request.Request;
import webserver.view.ModelAndView;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;

//Todo : 상속 이외의 구조를 사용, 하나 이상의 annotation 종류를 사용했을 때의 처리
public abstract class ArgumentResolver {

    private ArgResolveStrategy argResolveStrategy;  //전략 패턴

    public Map<String, String> getBody(Request req, List<Parameter> paramList, ModelAndView mv) throws IOException {
        Map<String, String> bodyMap;
        if(req.getRequestLine().getRequestMethod() == RequestMethod.POST)
            bodyMap = parseBody(req, paramList);
        else
            bodyMap = req.getRequestLine().getResource().getQueryString();
        return bodyMap;
    }

    public Object[] checkParameters(Request req, List<Parameter> paramList, ModelAndView mv) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        if(paramList.isEmpty())
            return new Object[]{};

        Parameter param = paramList.get(0);
        Class<?> paramType = param.getType();
        if(param.getAnnotatedType() instanceof PathVariable || paramType.equals(String.class) || paramType.equals(Integer.class) || paramType.equals(Float.class) || paramType.equals(Character.class) || paramType.equals(Double.class)){
            argResolveStrategy = FieldResolveStrategy.getInstance(); //Todo : 애노테이션 기반으로 처리
        }else if(paramType.equals(Map.class)){
            argResolveStrategy = MapResolveStrategy.getInstance();
        }else{
            argResolveStrategy = EntityResolveStrategy.getInstance();
        }

        Map<String, String> body = getBody(req,paramList, mv);
        if(param.getAnnotatedType() instanceof PathVariable)
            body = Map.class.cast(mv.getViewModel().get("pathVar"));

        return argResolveStrategy.argumentParsing(body, paramList);
    }

    public abstract Map<String, String> parseBody(Request req, List<Parameter> paramList) throws IOException;

}
