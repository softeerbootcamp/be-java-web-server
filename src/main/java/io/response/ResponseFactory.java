package io.response;

import enums.ContentType;
import enums.Status;
import io.request.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.DataOutputStream;
import java.nio.file.Files;

public class ResponseFactory {

    private static final String ABSOULTE_PATH = "/Users/rentalhub/Desktop/2주차/src/main/resources/templates/index.html";
    private static final Logger logger = LoggerFactory.getLogger(ResponseFactory.class);


    public Response create(Request request, DataOutputStream dos) {
        Status status = hasResource(request);
        ContentType contentType = getContentType(request.getUrl());
        byte[] messageBody = getResource(request);

        return new ResponseImpl(status, contentType, messageBody, dos);
    }

    private byte[] getResource(Request request) {
        try {
            return Files.readAllBytes(new File(String.format(ABSOULTE_PATH, request.getUrl())).toPath());
        } catch (IOException e) {
            logger.error("resource not found");
            return null;
        }
    }

    private Status hasResource(Request request) {
        try {
            Files.readAllBytes(new File(String.format(ABSOULTE_PATH, request.getUrl())).toPath());
            return Status.OK;
        } catch (IOException e) {
            return Status.NOT_FOUND;
        }
    }

    private ContentType getContentType(String requestUrl) {
        return ContentType.find(requestUrl);
    }
}
