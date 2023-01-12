package controller;

import request.HttpRequest;
import util.FileType;


public interface Controller {
    static Controller FactoryController(HttpRequest httpRequest) {
        FileType fileType = FileType.getFileType(httpRequest.getUrl());
        if (fileType != null) {
            return new FileController();
        } else {
            return new UserController();
        }

    }



}
