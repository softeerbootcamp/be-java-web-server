package webserver.argument.argumentResolveStrategies;

import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;

public class MapResolveStrategy implements ArgResolveStrategy {

    private MapResolveStrategy(){}

    public static MapResolveStrategy getInstance(){return MapResolveStrategy.LazyHolder.INSTANCE;}

    private static class LazyHolder{
        private static final MapResolveStrategy INSTANCE = new MapResolveStrategy();
    }

    @Override
    public Object[] argumentParsing(Map<String, String> body, List<Parameter> paramList) {
        return new Object[]{body};
    }
}
