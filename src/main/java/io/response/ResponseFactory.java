package io.response;

import enums.ContentType;
import enums.LogMessage;
import enums.Status;
import io.request.PathResolver;
import io.request.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.DataOutputStream;
import java.nio.file.Files;

public class ResponseFactory {

    private static final Logger logger = LoggerFactory.getLogger(ResponseFactory.class);

    private final PathResolver pathResolver = new PathResolver();

    public Response create(Request request, DataOutputStream dos) {
        FindResult findResult = findResource(request);

        return new HttpResponse(findResult, dos);
    }

    private FindResult findResource(Request request) {
        FindResult findResult = new FindResult();
        String resourcePath = pathResolver.resolve(request.getUrl());
        try {
            byte[] resource = Files.readAllBytes(new File(resourcePath).toPath());
            findResult.update(Status.OK, ContentType.find(resourcePath), resource);
        } catch (IOException e) {
            logger.info(LogMessage.RESOURCE_NOT_FOUND.getMessage());
        }
        return findResult;
    }
}
