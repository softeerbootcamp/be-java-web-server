package controller;

import utils.FileIoUtils;
import enums.ContentType;
import response.HttpResponse;
import enums.HttpStatus;


public class NotFoundExceptionHandler implements Controller {

    private static final String TEMPLATES_DIR = "./templates";
    private static final String CONTENT_LENGTH_HEADER_KEY = "Content-Length";



    public void writeErrorPage(HttpResponse response){
        showErrorPage(response);

    }

    public static void showErrorPage(HttpResponse res) {
        try{
            byte[] body = FileIoUtils.loadFileFromClasspath(TEMPLATES_DIR +"/notFound.html");
            ContentType contentType = ContentType.HTML;

            res.setStatus(HttpStatus.OK);
            res.setContentType(contentType);
            res.addToHeader(CONTENT_LENGTH_HEADER_KEY, String.valueOf(body.length));
            res.setBody(body);//body에는 요청한 파일 내용이 들어감
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

}
