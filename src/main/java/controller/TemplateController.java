package controller;

import request.Request;
import response.Response;

public class TemplateController implements Controller{
    private static final int File_Line_Index = 1;

    @Override
    public void selectedController(Request request, Response response) {
        System.out.println("firstLine : "+request);
        String[] filePath = request.split(" ");
        return filePath[File_Line_Index];
    }
}
