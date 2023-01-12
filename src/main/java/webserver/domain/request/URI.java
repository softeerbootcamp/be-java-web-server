package webserver.domain.request;

import java.util.HashMap;
import java.util.Map;
import static webserver.utils.CommonUtils.parseStringToList;
import static webserver.utils.HttpRequestUtils.parseQueryString;

public class URI {

    private Map<String, String> queryString;
    private String path;

    public URI(Map<String, String> queryString, String path) {
        this.queryString = queryString;
        this.path = path;
    }

    private static URI of(Map<String, String> method, String resource){
        return new URI(method, resource);
    }

    public static URI from(String passedPath){
        String [] parsedPath = parseStringToList(passedPath, "\\?");
        Map<String, String> queryStr = new HashMap<>();
        if(parsedPath.length > 1)
            queryStr = parseQueryString(parsedPath[1]);
        System.out.println(parsedPath[0]);

        return of(queryStr, parsedPath[0]);
    }

    public Map<String, String> getQueryString() {
        return queryString;
    }

    public String getPath() {
        return path;
    }
}
