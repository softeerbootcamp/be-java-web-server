package webserver.argument.argumentResolvers;

import com.fasterxml.jackson.databind.ObjectMapper;
import webserver.domain.request.Request;

import java.io.IOException;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;

public class JsonArgResolver extends ArgumentResolver{

    private JsonArgResolver(){}

    public static JsonArgResolver getInstance(){
        return JsonArgResolver.LazyHolder.INSTANCE;
    }

    private static class LazyHolder{  //Singleton
        private static final JsonArgResolver INSTANCE = new JsonArgResolver();
    }
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public Map<String, String> parseBody(Request req, List<Parameter> paramList) throws IOException {
        return mapper.readValue(req.getBody(), Map.class);
    }

}
