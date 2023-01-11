package webserver.domain.request;

public class RequestLine {

    private String method;
    private String resource;
    private final String version = "HTTP/1.1";

    private RequestLine(String method, String resource){
        this.method = method;
        this.resource = resource;
    }
    private static RequestLine of(String method, String resource){
        return new RequestLine(method, resource);
    }

    private static String[] parseRequestLine(String req){
        return req.split(" ");
    }

    public static RequestLine from(String req){
        String [] parsedReqLine = parseRequestLine(req);
        return of(parsedReqLine[0], parsedReqLine[1]);
    }

    public void readReqLine(){
        System.out.println(method + resource + version);
    }


    public String getResource(){
        return resource;
    }

}
