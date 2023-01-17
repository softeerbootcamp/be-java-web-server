package controller;

import reader.fileReader.FileReader;
import reader.fileReader.TemplatesFileReader;
import response.HttpResponse;
import util.FileType;
import util.HttpStatus;
import util.RequestDataType;
import util.Url;

import java.io.DataOutputStream;
import java.io.IOException;

public class ErrorController implements Controller{
    private final static String ERROR_404_ROUTE = "/error404.html";
    private final static String ERROR_500_ROUTE = "/error500.html";

    public static HttpResponse getErrorResponse(DataOutputStream dataOutputStream, HttpStatus httpStatus) throws IOException {
        FileReader fileReader = new TemplatesFileReader();
        byte[] data;
        switch (httpStatus) {
            case NOT_FOUND:
                data=fileReader.readFile(new Url(ERROR_404_ROUTE, RequestDataType.TEMPLATES_FILE));
                return new HttpResponse(new response.Data(dataOutputStream, data), FileType.HTML, HttpStatus.NOT_FOUND);
            default :
                data=fileReader.readFile(new Url(ERROR_500_ROUTE,RequestDataType.TEMPLATES_FILE));
                return new HttpResponse(new response.Data(dataOutputStream, data), FileType.HTML, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
