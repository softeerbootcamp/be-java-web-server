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

public class CustomHttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(CustomHttpRequest.class);
    private HttpMethod httpMethod;
    private String url;
    private String protocolVersion;
    private Map<String, String> urlParams;
    private Map<String, List<String>> requestHeaders;
    private List<String> requestBody;

    private CustomHttpRequest(String firstLine, List<String> headers, List<String> bodies) {
        setFirstLineHeaders(firstLine);
        setRequestHeaders(headers);
        setUrlParams(this.url);
        requestBody = bodies;
        if(this.httpMethod == HttpMethod.GET)
            logger.debug("url : " + this.url + ", method : " + this.httpMethod + ", urlParams : " + this.urlParams + ", body : " + this.requestBody);
        else
            logger.info("url : " + this.url + ", method : " + this.httpMethod + ", urlParams : " + this.urlParams + ", body : " + this.requestBody);

    }

    public static CustomHttpRequest of(String firstLine, List<String> headers, List<String> bodies) {
        return new CustomHttpRequest(firstLine, headers, bodies);
    }

    public static CustomHttpRequest from(InputStream inputStream) {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(inputStreamReader);
        try {
            String firstLine = br.readLine();
            List<String> headers = new ArrayList<>();

            while (br.ready()) {
                String nextLine = br.readLine();
                if (nextLine.equals(""))
                    break;
                headers.add(nextLine);
                logger.debug(nextLine);
            }
            StringBuilder sb = new StringBuilder();
            while (br.ready()) {
                int next = br.read();
                sb.append((char) next);
            }
            return of(firstLine, headers, List.of(sb.toString().split(System.lineSeparator())));
        }
        catch (IOException e){
            logger.error(e.getMessage());
            throw new RuntimeException();
        }
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

    public Map<String, String> parseBodyFromUrlEncoded() {
        Map<String, String> parsedBody = new HashMap<>();
        List<String> bodyParams = new ArrayList<>();
        for (String line : requestBody) {
            line = java.net.URLDecoder.decode(line, StandardCharsets.UTF_8);
            bodyParams.addAll(List.of(line.split("&")));
        }
        bodyParams.forEach(s -> {
            String[] split = s.split("=");
            parsedBody.put(split[0].trim(), split[1].trim());
        });
        return parsedBody;
    }

    private void setUrlParams(String url) {
        urlParams = new HashMap<>();
        if (url.contains("?")) {
            String tempParamStr = url.substring(url.indexOf("?") + 1);
            String[] params = tempParamStr.split("&");
            for (String param : params) {
                try {
                    String[] data = param.split("=");
                    urlParams.put(data[0], data[1]);
                } catch (Exception e) {
                    logger.error("String split failed : " + param);
                }
            }
            this.url = url.substring(0, url.indexOf("?"));
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

    public String getUrl() {
        return url;
    }

    public Map<String, String> getUrlParams() {
        return urlParams;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public HttpMethod getHttpMethod(){
        return httpMethod;
    }
}
