package request;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Map;

public class ResponseGeneratorImpl implements ResponseGenerator {
    private Map<String, String> parsedHeader;

    @Override
    public byte[] generate(InputStream in, int port) {
        parsedHeader = RequestParser.parse(in);
        System.out.println(port + ": " + parsedHeader);
        byte[] body = null;
        try {
            body = makeByte(RequestParser.getMethod(parsedHeader.get(RequestParser.REQUEST_LINE)), RequestParser.getResource(parsedHeader.get(RequestParser.REQUEST_LINE)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return body;
    }

    private byte[] makeByte(String method, String resource) throws IOException {
        byte[] body = null;
        if(method.equals("GET")) {
            body = handleGET(resource);
        }
        if(method.equals("POST")) {

        }
        if(method.equals("PUT")) {

        }
        if(method.equals("DELETE")) {

        }

        return body;
    }

    private byte[] handleGET(String resource) throws IOException {
        return Files.readAllBytes(new File("src/main/resources/"
                + (resource.contains("index.") || resource.contains(".ico") ? "templates" : "static") + resource).toPath());
    }
}
