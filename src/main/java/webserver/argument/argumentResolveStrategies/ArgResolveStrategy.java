package webserver.argument.argumentResolveStrategies;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;

public interface ArgResolveStrategy {

    Object[] argumentParsing(Map<String, String> body, List<Parameter> paramList) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;
}
