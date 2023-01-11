package webserver.domain.request;

public class RequestLine {

    private String method;
    private String resource;
    private String version;

    private RequestLine(String method, String resource, String version){
        this.method = method;
        this.resource = resource;
        this.version = version;
    }
    private static RequestLine of(String method, String resource, String version){
        return new RequestLine(method, resource, version);
    }

    private static String[] parseRequestLine(String req){
        return req.split(" ");
    }

    public static RequestLine from(String req){
        String [] parsedReqLine = parseRequestLine(req);
        return of(parsedReqLine[0], parsedReqLine[1], parsedReqLine[2]);
    }

    public void readReqLine(){
        System.out.println(method + resource + version);
    }

    public String getResource(){
        return resource;
    }

}
