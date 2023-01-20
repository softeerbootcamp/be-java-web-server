package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.FileService;
import utils.ExtensionUtils;
import utils.FileIoUtils;
import view.Model;
import enums.ContentType;
import request.HttpRequest;
import response.HttpResponse;

import java.io.FileNotFoundException;
import java.net.URL;

public class FileController implements Controller{

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    private static final String TEMPLATES_DIR = "./templates";
    private static final String STATIC_DIR = "./static";
    @Override
    public String doGet(HttpRequest request, HttpResponse response, Model model) throws FileNotFoundException,IllegalArgumentException {
        String url = request.getUrl();
        ContentType type = ExtensionUtils.extractExtension(url);
        boolean isTemplate = type.isTemplateDir(); //template 디렉토리 하위인지 확인
        if(!isTemplate) {
            FileService.serveFile(STATIC_DIR + url,response);
            return STATIC_DIR + url;
        }else if (isTemplate) {
            if(type == ContentType.ICO) FileService.serveFile(TEMPLATES_DIR + url,response);
            //TODO 더 리팩토링 해보기
            URL fileUrl = FileIoUtils.class.getClassLoader().getResource(TEMPLATES_DIR + url);
            if (fileUrl == null) {
                throw new FileNotFoundException();
            }
            return TEMPLATES_DIR + url;
        }

        return url;
    }

}
