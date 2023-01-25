package webserver.view;

import webserver.domain.ContentType;
import webserver.domain.StatusCodes;
import webserver.domain.request.Request;
import webserver.domain.response.Response;
import webserver.utils.StaticResourceFinder;

import java.io.IOException;

import static webserver.utils.StaticResourceFinder.getExtension;

/**
 * View for all non-dynamic resources
 */
public class CommonView implements View{

    private CommonView (){}

    public static CommonView getInstance(){
        return CommonView.LazyHolder.INSTANCE;
    }

    private static class LazyHolder{  //Singleton
        private static final CommonView INSTANCE = new CommonView();
    }

    @Override
    public void render (Request req, Response res, ModelAndView mv, String menuBar, String nameTag) throws IOException{
        String resourceUrl = mv.getViewPath();
        StaticResourceFinder.staticFileResolver(resourceUrl).ifPresentOrElse( fileAsBytes -> {  //if file exists
                    String fileContent = new String(fileAsBytes);
                    if(getExtension(resourceUrl) == ContentType.TEXT_HTML){
                        fileContent = fileContent.replace(menuBar, "")
                                                 .replace("<li id=\"username\"></li>", nameTag);
                    }
                    res.addHeaderAndBody(StatusCodes.OK, fileContent.getBytes(), getExtension(resourceUrl));}
                    ,()-> res.notFoundError(StatusCodes.NOT_FOUND)); //if not
    }
}
