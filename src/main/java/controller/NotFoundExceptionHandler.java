package controller;

import utils.FileIoUtils;
import enums.ContentType;
import response.HttpResponse;
import enums.HttpStatus;


public class NotFoundExceptionHandler implements Controller {

    private static final String TEMPLATES_DIR = "./templates";
    private static final String CONTENT_LENGTH_HEADER_KEY = "Content-Length";

    public static void showErrorPage(HttpResponse res) {
        try{
            String path ="./templates/notFound.html";
            System.out.println(path);
            res.setStatus(HttpStatus.NOT_FOUND);
            res.setContentType(ContentType.HTML);
            byte[] body = FileIoUtils.loadFileFromClasspath(path);
            res.addToHeader("Content-Length", String.valueOf(body.length));
            res.setBody(body);//body에는 요청한 파일 내용이 들어감
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

}
