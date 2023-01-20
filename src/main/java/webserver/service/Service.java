package webserver.service;

import webserver.Paths;
import webserver.httpUtils.Request;
import webserver.httpUtils.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public interface Service {
    default Response exec(Request req) throws IOException {
        String reqQuery = req.getReqLine().getQuery();
        String contentType = Files.probeContentType(new File(reqQuery).toPath());
        byte[] bodyByte = urlToByte(reqQuery);

        return new Response().withVersion(req.getReqLine().getVersion())
                .withStatCodeAndText(200, "OK")
                .withHeaderKeyVal("Content-Type", contentType + ";charset=utf-8")
                .withHeaderKeyVal("Content-Length", Integer.toString(bodyByte.length))
                .withBodyBytes(bodyByte);
    };

    static byte[] urlToByte(String url) throws IOException {
        if (url.contains("html") || url.contains("favicon"))
            return Files.readAllBytes(new File(Paths.TEMPLATE_PATH + url).toPath());

        return Files.readAllBytes(new File(Paths.STATIC_PATH + url).toPath());
    }

}
