package controller;

import request.HttpRequest;
import util.FileType;
import util.Url;


public interface Controller {
    static Controller FactoryController(Url url) {
        FileType fileType = FileType.getFileType(url);
        if (fileType != null) {
            return new FileController();
        } else if(url.getUrl().matches(UserController.REGEX)){
            return new UserController();
        }else{
            return new ErrorController();
        }

    }




}
