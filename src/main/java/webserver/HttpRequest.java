package webserver;

import utils.HttpRequestUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
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

    public Map<String, String> getHeaders(){
        return this.headers;
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

    public Map<String,String> parseBody() throws IOException {
        Map<String, String> parsedBody = new HashMap<>();
        //1. body가 없으면 빈 map을 리턴
        if(body == null) return Collections.EMPTY_MAP;
        //2. 요청의 contentType이 application/x-www-form-urlencoded이면 폼 내용을 파싱
        String contentType = headers.get("Content-Type");
        if(contentType.equals("application/x-www-form-urlencoded")) parsedBody= HttpRequestUtils.parseQueryString(body);
        return parsedBody;
    }


}
