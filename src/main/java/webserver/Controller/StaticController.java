package webserver.Controller;

import webserver.domain.ContentType;
import webserver.domain.StatusCodes;
import webserver.domain.response.Response;
import webserver.utils.StaticResourceFinder;

import java.io.IOException;
import java.util.Map;

public class StaticController implements Controller {
    @Override
    public void chain(String path, Map<String, String> queryString, Response res) throws IOException {
        StaticResourceFinder.staticFileResolver(path).ifPresentOrElse(
                (fileAsBytes) -> res.ok(StatusCodes.OK, fileAsBytes, StaticResourceFinder.getExtension(path)),  //if file exists
                ()-> res.notFoundError(StatusCodes.NOT_FOUND)); //if not
    }
}
