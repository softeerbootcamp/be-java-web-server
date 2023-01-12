package request;

import parser.StringParser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Uri {
    private final String path;
    private final Map<String, String> parameters;

    public Uri(String path, Map<String, String> parameters) {
        this.path = path;
        this.parameters = parameters;
    }

    public static Uri of(String uri) {
        String[] uriSources =  uri.split("\\?");
        String path = uriSources[0];
        Map<String, String> parameters = new HashMap<>();

//        if (uriSources.length > 1) {
//            Arrays.stream(uriSources[1].split("&"))
//                    .forEach(parameter -> parameters.put(parameter.split("=")[0], parameter.split("=")[1]));
//        }

        parameters = StringParser.dataParsing(uri);
        return new Uri(path, parameters);
    }

    private static boolean existParameters(String[] uriSources) {
        return uriSources.length > 1;
    }

    private static void extractParameter(String uriSource, Map<String, String> parameters) {
        for (String parameter : uriSource.split("&")) {
            String[] keyAndValue = parameter.split("=");
            if (keyAndValue.length >= 2) {
                parameters.put(keyAndValue[0], keyAndValue[1]);
                continue;
            }
            parameters.put(keyAndValue[0], "");
        }
    }

    public boolean isTemplatesResource() {
        return path.endsWith("html") || path.equals("/favicon.ico");
    }

    public boolean isStaticResource() {
        return !isTemplatesResource() && path.contains(".");
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }
}
