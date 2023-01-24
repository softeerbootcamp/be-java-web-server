package webserver.argument.argumentResolveStrategies;

import webserver.domain.StatusCodes;
import webserver.exception.HttpRequestException;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FieldResolveStrategy implements ArgResolveStrategy{

    private FieldResolveStrategy(){}

    public static FieldResolveStrategy getInstance(){return FieldResolveStrategy.LazyHolder.INSTANCE;}

    private static class LazyHolder{
        private static final FieldResolveStrategy INSTANCE = new FieldResolveStrategy();
    }
    @Override
    public Object[] argumentParsing(Map<String, String> body, List<Parameter> paramList) {
        List<String> paramObjList = new ArrayList<>();
        paramList.forEach(param -> {
            String bodyContent = body.get(param.getName());
            if(bodyContent == null)
                throw new HttpRequestException(StatusCodes.BAD_REQUEST, "<script>alert('입력값을 확인해주세요'); history.go(-1);</script>");
            paramObjList.add(bodyContent);
        });
        return paramObjList.toArray();
    }
}
