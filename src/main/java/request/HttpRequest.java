package request;

import reader.RequestReader;
import util.HttpMethod;
import util.Url;
import util.UrlType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class HttpRequest {

    public static final String REQUEST_LINE = "Request Line";

    private final HashMap<String, String> headerContents;
    private final HttpMethod httpMethod;

    private final Url url;


    public HttpRequest(HashMap<String, String> headerContents) {
        this.headerContents = headerContents;
        this.httpMethod = HttpMethod.findMethod(this);

        String urlContext = RequestReader.findPathInRequest(this);
        this.url = new Url(urlContext, UrlType.getUrlType(urlContext));
    }

    public static HttpRequest getHttpRequest(InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        HashMap<String,String > requestHeaders = new HashMap<>();

        requestHeaders.put(REQUEST_LINE, bufferedReader.readLine());

        while (bufferedReader.ready()) {
            String headerContent = bufferedReader.readLine();
            if(headerContent.contains(": ")){
                String[] line = headerContent.split(": ");
                requestHeaders.put(line[0], line[1]);
            }
        }
        return new HttpRequest(requestHeaders);
    }



    public HashMap<String, String> getHeaderContents() {
        return headerContents;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public Url getUrl() {
        return url;
    }
}
