package request.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.Request;
import request.RequestHandler;
import response.HttpResponseStatus;
import response.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

public class StaticFileHandler implements RequestHandler {
    Logger logger = LoggerFactory.getLogger(StaticFileHandler.class);

    private static final StaticFileHandler instance;

    static {
        instance = new StaticFileHandler();
    }

    private StaticFileHandler() {}

    public static StaticFileHandler getInstance() {
        return instance;
    }

    @Override
    public Response doGet(Request request) {
        try {
            byte[] file = Files.readAllBytes(new File("src/main/resources/static" + request.getResource()).toPath());
            return Response.createSimpleResponse(file, request.getResourceFileContentType(), HttpResponseStatus.OK);
        } catch (IOException e) {
            logger.error(Arrays.toString(e.getStackTrace()));
            return Response.from(HttpResponseStatus.NOT_FOUND);
        }
    }
}
