package webserver.Controller;

import webserver.domain.StatusCodes;
import webserver.exception.HttpRequestException;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import static webserver.utils.HttpResponseUtil.makeResponse;
import static webserver.utils.StaticControllerUtil.staticFileResolver;

public class StaticController implements Controller{

    @Override
    public void handle(String resource, OutputStream out) throws HttpRequestException, IOException{
        byte[] fileAsByte = staticFileResolver(resource);
        StatusCodes statusCode = StatusCodes.findStatus("200");
        makeResponse(fileAsByte, out, statusCode.getStatusCode(), statusCode.getStatusMsg());  //Create Http Response Message
    }
}
