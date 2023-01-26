package webserver.argument.argumentResolveStrategies;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;

public class EntityResolveStrategy implements ArgResolveStrategy{

    private EntityResolveStrategy(){}

    public static EntityResolveStrategy getInstance(){return EntityResolveStrategy.LazyHolder.INSTANCE;}

    private static class LazyHolder{
        private static final EntityResolveStrategy INSTANCE = new EntityResolveStrategy();
    }

    @Override
    public Object[] argumentParsing(Map<String, String> body, List<Parameter> paramList) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> clazz = paramList.get(0).getType();
        Method method = clazz.getMethod("from", Map.class);
        return new Object[]{clazz.cast(method.invoke(null, body))};
    }
}
