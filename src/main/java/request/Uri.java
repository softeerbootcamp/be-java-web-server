package request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.StringParser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Uri {
    private static final Logger logger = LoggerFactory.getLogger(Uri.class);

    private final String path;
    private final Map<String, String> parameters;

    public Uri(String path, Map<String, String> parameters) {
        this.path = path;
        this.parameters = parameters;
    }

    public static Uri of(String uri) {
        String[] uriSources =  uri.split("\\?");
        String path = uriSources[0];
        Map<String, String> parameters = StringParser.dataParsing(uri);
        return new Uri(path, parameters);
    }

    public boolean isTemplatesResource() {
        logger.debug("[ Uri ] isTemplateResource : {}",path);
        return path.endsWith("html") || path.equals("/favicon.ico");
    }

    public boolean isStaticResource() {
        logger.debug("[ Uri ] isStaticResource : {}",path);
        return !isTemplatesResource() && path.contains(".");
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }
}
