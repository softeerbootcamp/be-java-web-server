package webserver.argument;

import webserver.argument.argumentResolvers.ArgumentResolver;
import webserver.argument.argumentResolvers.JsonArgResolver;
import webserver.argument.argumentResolvers.TextPlainArgResolver;
import webserver.argument.argumentResolvers.UrlEncodedArgResolver;
import webserver.domain.ContentType;

import java.util.Map;

public class ArgumentResolverMapping {

    private static Map<ContentType, ArgumentResolver> argResolverMap = Map.of(ContentType.FORM_URLENCODED, UrlEncodedArgResolver.getInstance(), ContentType.APPLICATION, JsonArgResolver.getInstance(), ContentType.TEXT_PLAIN, TextPlainArgResolver.getInstance());

    public static ArgumentResolver findArgumentResolver(String contentTypeStr){
        if(contentTypeStr == null)
            return UrlEncodedArgResolver.getInstance();
        ContentType contentType = ContentType.findByType(contentTypeStr);
        return argResolverMap.get(contentType);
    }

}
