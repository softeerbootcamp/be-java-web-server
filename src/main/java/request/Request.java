package request;

import file.FileContentType;

import java.io.InputStream;
import java.util.Map;

public class Request {
    private final Map<String, String> requestHeader;

    private Request(InputStream in) {
        requestHeader = RequestParser.parse(in);
    }

    public static Request of(InputStream in) {
        return new Request(in);
    }

    public String getMethod() {
        String[] tokens = requestHeader.get(RequestParser.REQUEST_LINE).split(" ");
        return tokens[0];
    }

    public String getResource() {
        String[] tokens = requestHeader.get(RequestParser.REQUEST_LINE).split(" ");
        return tokens[1];
    }

    public String getResourceFileContentType() {
        int index = requestHeader.get(RequestParser.REQUEST_LINE).indexOf(".");
        String postfix = requestHeader.get(RequestParser.REQUEST_LINE).substring(index+1);
        for(FileContentType fileContentType : FileContentType.values()) {
            if(postfix.equals(fileContentType.getPostfix())) {
                return fileContentType.getContentType();
            }
        }
        return FileContentType.NO_MATCH.getContentType();
    }

    @Override
    public String toString() {
        return requestHeader.toString();
    }
}
