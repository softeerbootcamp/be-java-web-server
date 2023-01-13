package webserver.domain.request;

import static webserver.utils.CommonUtils.parseStringToList;

public class RequestLine {

    private String method;
    private URI resource;
    private final String version = "HTTP/1.1";

    private RequestLine(String method, URI resource){
        this.method = method;
        this.resource = resource;
    }
    private static RequestLine of(String method, URI resource){
        return new RequestLine(method, resource);
    }

    public static RequestLine from(String req){
        String [] parsedReqLine = parseStringToList(req, " ");
        URI resourceURI = URI.from(parsedReqLine[1]);
        return of(parsedReqLine[0], resourceURI);
    }


    public URI getResource(){
        return resource;
    }

}
