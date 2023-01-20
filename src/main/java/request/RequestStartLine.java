package request;

import enums.HttpMethod;
import utils.HttpRequestUtils;

import java.util.HashMap;
import java.util.Map;

public class RequestStartLine {

    private static final String DELIMITER = " ";
    private static final String PATH_QUERY_DELIMITER = "?";
    private static final String PATH_QUERY_DELIMITER_REGEX = "\\?";

    private final HttpMethod method;
    private final String url;
    private final Map<String, String> queries;

    public RequestStartLine(HttpMethod method, String url, Map<String, String> queries) {
        this.method = method;
        this.url = url;
        this.queries = queries;
    }

    public static RequestStartLine from(String str){
        //GET /user/create?userId=user&password=66a41ad2-bda9-4c70-807c-e0e13ff04475&name=1234&email=1234%40khu.ac.kr http/1.1
        String[] tokens = str.trim().split(DELIMITER);//trim : 문자열의 양 사이드 공백을 제거
        HttpMethod method = HttpMethod.valueOf(tokens[0]); //해당 값을 통해 HttpMethod를 찾아냄

        boolean isQuery = false;
        if(tokens[1].contains(PATH_QUERY_DELIMITER)) isQuery = true;

        String targetUrl=" ";
        Map<String,String> quries = new HashMap<>();

        if(isQuery){
            targetUrl = tokens[1].split(PATH_QUERY_DELIMITER_REGEX)[0];
            quries = getQuriesFromStr(tokens[1].split(PATH_QUERY_DELIMITER_REGEX)[1]);
        }

        if(!isQuery){
            targetUrl = tokens[1];
        }

        return new RequestStartLine(method, targetUrl, quries);

    }

    public static Map<String,String> getQuriesFromStr(String str){
        Map<String,String> quries = new HashMap<>();
        quries = HttpRequestUtils.parseQueryString(str);
        return quries;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getQueries() {
        return queries;
    }
}
