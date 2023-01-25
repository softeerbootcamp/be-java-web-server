package webserver.argument.argumentResolvers;


import webserver.domain.request.Request;
import webserver.utils.HttpRequestUtils;

import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;

public class UrlEncodedArgResolver extends ArgumentResolver {

    private UrlEncodedArgResolver(){}

    public static UrlEncodedArgResolver getInstance(){
        return UrlEncodedArgResolver.LazyHolder.INSTANCE;
    }

    private static class LazyHolder{  //Singleton
        private static final UrlEncodedArgResolver INSTANCE = new UrlEncodedArgResolver();
    }

    @Override
    public Map<String, String> parseBody(Request req, List<Parameter> paramList) {
        return HttpRequestUtils.parseQueryString(req.getBody());
    }

}
