package webserver.argument.argumentResolvers;

import webserver.domain.request.Request;

import java.io.IOException;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;

public class TextPlainArgResolver extends ArgumentResolver{

    private TextPlainArgResolver(){}

    public static TextPlainArgResolver getInstance(){return TextPlainArgResolver.LazyHolder.INSTANCE;}

    private static class LazyHolder{
        private static final TextPlainArgResolver INSTANCE = new TextPlainArgResolver();
    }

    @Override
    public Map<String, String> parseBody(Request req, List<Parameter> paramList) throws IOException {
        return Map.of("body", req.getBody());
    }
}
