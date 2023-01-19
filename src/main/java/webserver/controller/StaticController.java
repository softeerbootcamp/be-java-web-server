package webserver.controller;

import webserver.domain.StatusCodes;
import webserver.domain.request.Request;
import webserver.domain.response.Response;
import webserver.exception.HttpRequestException;
import webserver.utils.StaticResourceFinder;

import java.io.IOException;

public class StaticController implements Controller {

    private StaticController (){}

    public static StaticController getInstance(){
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder{
        private static final StaticController INSTANCE = new StaticController();
    }
    @Override
    public void chain(Request req, Response res) throws HttpRequestException, IOException {

        String path = req.getRequestLine().getResource().getPath();  //리소스 위치 경로

        StaticResourceFinder.staticFileResolver(path).ifPresentOrElse(
                (fileAsBytes) -> res.ok(StatusCodes.OK, fileAsBytes, StaticResourceFinder.getExtension(path)),  //if file exists
                ()-> res.notFoundError(StatusCodes.NOT_FOUND)); //if not
    }
}
