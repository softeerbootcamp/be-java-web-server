package controller;

import reader.fileReader.FileReader;
import reader.fileReader.TemplatesFileReader;
import response.Data;
import response.HttpResponse;
import util.FileType;
import util.HttpStatus;
import request.RequestDataType;
import request.Url;

import java.io.DataOutputStream;
import java.io.IOException;

public class ErrorController implements Controller{
    private final static String ERROR_404_ROUTE = "/error404.html";
    private final static String ERROR_403_ROUTE = "/error403.html";
    private final static String ERROR_401_ROUTE = "/user/login_failed.html";
    private final static String ERROR_500_ROUTE = "/error500.html";

    public static HttpResponse getErrorResponse(DataOutputStream dataOutputStream, HttpStatus httpStatus) throws IOException {
        FileReader fileReader = new TemplatesFileReader();
        byte[] data;
        switch (httpStatus) {
            case NOT_FOUND:
                data=fileReader.readFile(new Url(ERROR_404_ROUTE, RequestDataType.TEMPLATES_FILE));
                return new HttpResponse.Builder()
                        .setData(new Data(dataOutputStream,data))
                        .setFileType(FileType.HTML)
                        .setHttpStatus(HttpStatus.NOT_FOUND)
                        .build();
            case METHOD_NOT_ALLOWED:
                data=fileReader.readFile(new Url(ERROR_403_ROUTE, RequestDataType.TEMPLATES_FILE));
                return new HttpResponse.Builder()
                        .setData(new Data(dataOutputStream,data))
                        .setFileType(FileType.HTML)
                        .setHttpStatus(HttpStatus.METHOD_NOT_ALLOWED)
                        .build();
            case UN_AUTHORIZED:
                data=fileReader.readFile(new Url(ERROR_401_ROUTE, RequestDataType.TEMPLATES_FILE));
                return new HttpResponse.Builder()
                        .setData(new Data(dataOutputStream,data))
                        .setFileType(FileType.HTML)
                        .setHttpStatus(HttpStatus.NOT_FOUND)
                        .build();
            default :
                data=fileReader.readFile(new Url(ERROR_500_ROUTE,RequestDataType.TEMPLATES_FILE));
                return new HttpResponse.Builder()
                        .setData(new Data(dataOutputStream,data))
                        .setFileType(FileType.HTML)
                        .setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .build();

        }

    }

}
