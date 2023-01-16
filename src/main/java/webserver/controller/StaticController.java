package webserver.controller;

import webserver.domain.StatusCodes;
import webserver.domain.request.Request;
import webserver.domain.response.Response;
import webserver.exception.HttpRequestException;
import webserver.utils.StaticResourceFinder;

import java.io.IOException;
import java.util.Map;

public class StaticController implements Controller {

    @Override
    public void chain(Request req, Response res) throws HttpRequestException, IOException {
        String path = req.getRequestLine().getResource().getPath();
        StaticResourceFinder.staticFileResolver(path).ifPresentOrElse(
                (fileAsBytes) -> res.ok(StatusCodes.OK, fileAsBytes, StaticResourceFinder.getExtension(path)),  //if file exists
                ()-> res.notFoundError(StatusCodes.NOT_FOUND)); //if not
    }
}
