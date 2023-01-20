package request;

import enums.HttpMethod;
import session.HttpSession;
import session.HttpSessionManager;
import utils.HttpRequestUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    public static final String SID_ATTR_KEY = "sid";

    private final HttpMethod method;
    private String url;
    //private final String host;
    private final Map<String, String> queries;

    private final Map<String, String> cookies;
    private final Map<String, String> headers;
    private final String body;

    private HttpSession httpSession;

    public HttpRequest(HttpMethod method, String url, Map<String, String> queries, Map<String, String> cookies, Map<String, String> headers, String body){
        this.method = method;
        this.url = url;
        this.queries = queries;
        this.cookies = cookies;
        this.headers = headers;
        this.body = body;

        if(cookies.containsKey(SID_ATTR_KEY)){
            //System.out.println("request에서 쿠키 파싱을 통해 얻은 sid를 통해 얻은 유저 정보");
            httpSession = HttpSessionManager.getSession(cookies.get(SID_ATTR_KEY));
            //System.out.println(httpSession.getUserInfo());
        }
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

    public Map<String, String>  getCookies(){ return cookies;}
    public String getCookie(String key) {
        return cookies.get(key);
    }

    public HttpSession getSession() {
        if (this.httpSession == null) {
            this.httpSession = HttpSessionManager.getSession(cookies.get(SID_ATTR_KEY));
        }
        return this.httpSession;
    }


    public void setUrl(String url){
        this.url = url;
    }
    public String getUrl() {
        return url;
    }
    public String getBody() {
        return body;
    }

    public Map<String,String> parseBody() {
        Map<String, String> parsedBody = new HashMap<>();
        //1. body가 없으면 빈 map을 리턴
        if(body.isEmpty()) return Collections.EMPTY_MAP;
        //2. 요청의 contentType이 application/x-www-form-urlencoded이면 폼 내용을 파싱
        String contentType = headers.get("Content-Type");
        if(contentType.equals("application/x-www-form-urlencoded")) parsedBody= HttpRequestUtils.parseQueryString(body);
        return parsedBody;

    }


}
