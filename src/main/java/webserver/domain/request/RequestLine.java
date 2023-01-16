package webserver.domain.request;

import webserver.domain.RequestMethod;

import static webserver.utils.CommonUtils.parseStringToList;

public class RequestLine {

    private RequestMethod method;
    private URI resource;
    private final String version = "HTTP/1.1";

    private RequestLine(RequestMethod method, URI resource){
        this.method = method;
        this.resource = resource;
    }
    private static RequestLine of(RequestMethod method, URI resource){
        return new RequestLine(method, resource);
    }

    public static RequestLine from(String req){
        String [] parsedReqLine = parseStringToList(req, " ");
        RequestMethod method = RequestMethod.findType(parsedReqLine[0]);
        URI resourceURI = URI.from(parsedReqLine[1]);
        return of(method, resourceURI);
    }

    public RequestMethod getRequestMethod(){
        return method;
    }

    public URI getResource(){
        return resource;
    }

}
