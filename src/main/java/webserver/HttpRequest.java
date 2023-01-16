package webserver;

import java.util.Map;

public class HttpRequest {

    private final HttpMethod method;
    private final String url;
    //private final String host;
    private final Map<String, String> queries;
    private final Map<String, String> headers;
    private final String body;


    public HttpRequest(HttpMethod method, String url, Map<String, String> queries,Map<String, String> headers,String body){
        this.method = method;
        this.url = url;
        //this.host = host;
        this.queries = queries;
        this.headers = headers;
        this.body = body;
    }


    public boolean matchMethod(HttpMethod method){
        return this.method.equals(method);
    }

    public String getQueryByKey(String key){
        return queries.get(key);
    }

    public String getHeaderByKey(String key) {
        return headers.get(key);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }
    public String getBody() {
        return body;
    }

}
