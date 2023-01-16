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
    private final static String ERROR_404_ROUTE = "/error404.html";
    private final static String ERROR_500_ROUTE = "/error500.html";


    public static HttpResponse get404ErrorResponse(DataOutputStream dataOutputStream) throws IOException {
        FileReader fileReader = new TemplatesFileReader();

        byte[] data = fileReader.readFile(new Url(ERROR_404_ROUTE, UrlType.TEMPLATES_FILE));

        return new HttpResponse(new Data(dataOutputStream, data), FileType.HTML, HttpStatus.NOT_FOUND);
    }

    public static HttpResponse get500ErrorResponse(DataOutputStream dataOutputStream) throws IOException {
        FileReader fileReader = new TemplatesFileReader();

        byte[] data = fileReader.readFile(new Url(ERROR_500_ROUTE, UrlType.TEMPLATES_FILE));

        return new HttpResponse(new Data(dataOutputStream, data), FileType.HTML, HttpStatus.NOT_FOUND);
    }

}
