package http.response;

import http.HttpStatus;

public class HttpStatusLine {
    HttpStatus httpStatus;
    String httpVersion;

    public HttpStatusLine(HttpStatus status, String httpVersion){
        httpStatus = status;
        this.httpVersion = httpVersion;
    }

    public boolean checkStatus(HttpStatus status){
        return this.httpStatus.equals(status);
    }

    public String toStringForResponse(){
        return httpVersion + " " + httpStatus.toString() + System.lineSeparator();
    }

}
