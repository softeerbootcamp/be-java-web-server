package webserver.argument.argumentResolvers;

import webserver.argument.argumentResolveStrategies.ArgResolveStrategy;
import webserver.argument.argumentResolveStrategies.EntityResolveStrategy;
import webserver.argument.argumentResolveStrategies.FieldResolveStrategy;
import webserver.argument.argumentResolveStrategies.MapResolveStrategy;
import webserver.domain.RequestMethod;
import webserver.domain.request.Request;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;

//Todo : 상속 이외의 구조를 사용
public abstract class ArgumentResolver {

    private ArgResolveStrategy argResolveStrategy;

    public Map<String, String> getBody(Request req, List<Parameter> paramList) throws IOException {
        Map<String, String> body;
        if(req.getRequestLine().getRequestMethod() == RequestMethod.POST)
            body = parseBody(req, paramList);
        else
            body = req.getRequestHeader();
        return body;
    }

    public Object[] checkParameters(Request req, List<Parameter> paramList) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        if(paramList.isEmpty())
            return new Object[]{};

        Class<?> paramType = paramList.get(0).getType();
        if(paramType.equals(String.class) || paramType.equals(Integer.class) || paramType.equals(Float.class) || paramType.equals(Character.class) || paramType.equals(Double.class)){
            argResolveStrategy = FieldResolveStrategy.getInstance(); //Todo : 애노테이션 기반으로 처리
        }else if(paramType.equals(Map.class)){
            argResolveStrategy = MapResolveStrategy.getInstance();
        }else{
            argResolveStrategy = EntityResolveStrategy.getInstance();
        }

        Map<String, String> body = getBody(req,paramList);
        return argResolveStrategy.argumentParsing(body, paramList);
    }

    public abstract Map<String, String> parseBody(Request req, List<Parameter> paramList) throws IOException;

    public void setArgResolveStrategy(ArgResolveStrategy strategy){
        argResolveStrategy = strategy;
    }

}
