package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.FileService;
import utils.ExtensionUtils;
import view.Model;
import webserver.ContentType;
import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.RequestHandler;

import java.io.IOException;
import java.net.URISyntaxException;

public class FileController implements Controller{

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String TEMPLATES_DIR = "./templates";
    private static final String STATIC_DIR = "./static";
    private FileService fileService = new FileService();
    @Override
    public String doGet(HttpRequest request, HttpResponse response, Model model) throws IOException, URISyntaxException {
        String url = request.getUrl();
        ContentType type = ExtensionUtils.extractExtension(url);
        boolean isTemplate = type.isTemplateDir(); //template 디렉토리 하위인지 확인
        if(!isTemplate) {
            fileService.serveFile(STATIC_DIR + url,request,response);
            return STATIC_DIR + url;
        }
        else if (isTemplate) {
            if(type == ContentType.ICO) fileService.serveFile(TEMPLATES_DIR + url,request,response);
            return TEMPLATES_DIR + url;
        }
        return url;
    }

}
