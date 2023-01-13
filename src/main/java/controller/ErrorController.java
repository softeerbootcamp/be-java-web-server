package controller;

import model.User;
import reader.RequestGetReader;
import reader.fileReader.FileReader;
import reader.fileReader.TemplatesFileReader;
import request.HttpRequest;
import response.Data;
import response.HttpResponse;
import util.FileType;
import util.HttpStatus;
import util.Url;
import util.UrlType;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class ErrorController implements Controller{
    private final static String ERROR_ROUTE = "/error.html";


    public static HttpResponse getErrorResponse(DataOutputStream dataOutputStream) throws IOException {
        FileReader fileReader = new TemplatesFileReader();

        byte[] data = fileReader.readFile(new Url(ERROR_ROUTE, UrlType.TEMPLATES_FILE));

        return new HttpResponse(new Data(dataOutputStream,data), FileType.HTML, HttpStatus.NOT_FOUND);
    }

}
