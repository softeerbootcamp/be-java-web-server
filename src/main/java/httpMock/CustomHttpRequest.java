package httpMock;

import httpMock.constants.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static webserver.RequestHandler.CRLF;

public class CustomHttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(CustomHttpRequest.class);
    private Map<String, String> requestParams;
    private Map<String, List<String>> requestHeaders;
    private Map<String, String> requestBodies;
    private HttpMethod httpMethod;
    private String protocolVersion;
    private String url;

    public static CustomHttpRequest from(InputStream inputStream){
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(inputStreamReader);
        try {
            String firstLine = br.readLine();
            List<String> headers = new ArrayList<>();
            List<String> bodies = new ArrayList<>();
            while (br.ready()) {
                String nextLine = br.readLine();
                if(nextLine.equals(CRLF))
                    break;
                headers.add(nextLine);
            }
            while (br.ready()){
                String nextLine = br.readLine();
                if(nextLine.equals(CRLF))
                    break;
                bodies.add(nextLine);
            }
            return of(firstLine, headers, bodies);
        }
        catch (IOException e){
            logger.error(e.getMessage());
            throw new RuntimeException();
        }
    }

    public static CustomHttpRequest of(String firstLine, List<String> headers, List<String> bodies){
        return new CustomHttpRequest(firstLine, headers, bodies);
    }

    private CustomHttpRequest(String firstLine, List<String> headers, List<String> bodies) {
        setFirstLineHeaders(firstLine);
        setRequestHeaders(headers);
        setRequestParams(this.url);
        setRequestBody(bodies);
        logger.info("url : " + this.url + ", method : " + this.httpMethod + ", params : " + this.requestParams);
    }

    private void setFirstLineHeaders(String firstLine) {
        String[] firstLineDatas = firstLine.split("\\s");

        if (firstLineDatas.length != 3)
            throw new IllegalArgumentException("First Line of Http must have 2 space : " + firstLine);

        if (firstLineDatas[1].equals("/") || firstLineDatas[1].equals(""))
            firstLineDatas[1] = "/index.html";

        this.httpMethod = HttpMethod.valueOf(firstLineDatas[0]);
        this.url = java.net.URLDecoder.decode(firstLineDatas[1], StandardCharsets.UTF_8);
        this.protocolVersion = firstLineDatas[2];
    }

    private void setRequestHeaders(List<String> headers){
        requestHeaders = new HashMap<>();
        headers.forEach(this::addToRequestHeader);
    }

    //TODO: 아래 body parsing 코드는 queryString 형태로 들어옴을 가정하고 있습니다.
    private void setRequestBody(List<String> bodyLines){
        requestBodies = new HashMap<>();
        List<String> bodyParams = new ArrayList<>();
        for(String line : bodyLines){
            bodyParams.addAll(List.of(line.split("&")));
        }
        bodyParams.forEach(this::addToRequestBody);
    }

    private void setRequestParams(String url) {
        requestParams = new HashMap<>();
        if (url.contains("?")) {
            String tempParamStr = url.substring(url.indexOf("?") + 1);
            String[] params = tempParamStr.split("&");
            for (String param : params) {
                try {
                    String[] data = param.split("=");
                    requestParams.put(data[0], data[1]);
                } catch (Exception e) {
                    logger.error("String split failed : " + param);
                }
            }
        }
    }

    private void addToRequestHeader(String line) {
        String[] split1 = line.split(":");
        String itemName = split1[0].trim();
        if (split1.length < 2) {
            return;
        }
        String[] itemString = Arrays.stream(split1[1].split(",")).map(String::trim).toArray(String[]::new);
        List<String> itemList = new ArrayList<>(List.of(itemString));
        this.requestHeaders.put(itemName, itemList);
    }

    private void addToRequestBody(String line){
        String[] split = line.split("=");
        this.requestBodies.put(split[0], split[1]);
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getRequestParams() {
        return requestParams;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public HttpMethod getHttpMethod(){
        return httpMethod;
    }
}
