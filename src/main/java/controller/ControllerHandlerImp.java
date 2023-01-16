package controller;

import request.HttpRequest;
import response.HttpResponse;
import util.FileType;
import util.Url;
import util.UrlType;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public enum ControllerHandlerImp implements ControllerHandler{
    FILE_CONTROLLER(FileType.getAllExtension()){
        public void handle(Url url, DataOutputStream dataOutputStream, HttpRequest httpRequest) throws IOException {
            if (url.getUrlType().equals(UrlType.TEMPLATES_FILE)) {
                new FileController().TemplateController(dataOutputStream, httpRequest);
            } else if (url.getUrlType().equals(UrlType.STATIC_FILE)) {
                 new FileController().StaticController(dataOutputStream, httpRequest);
            }
        }
    },
    MEMBER_CONTROLLER(List.of("/user/create")){
        public void handle(Url url, DataOutputStream dataOutputStream, HttpRequest httpRequest) throws IOException {
            if (url.getUrlType().equals(UrlType.QUERY_STRING)&&url.getUrl().matches(UserController.REGEX)) {
                new UserController().UserQueryString(dataOutputStream, httpRequest);
            }
        }
    },

    ERROR_CONTROLLER(List.of()){
        public void handle(Url url, DataOutputStream dataOutputStream, HttpRequest httpRequest) throws IOException {
            new ErrorController().getErrorResponse(dataOutputStream);
        }
    };

    private List<String> regexes;

    ControllerHandlerImp(List<String> regexes){
        this.regexes = regexes;
    }

    public static ControllerHandlerImp findController(Url url) {
        return Arrays.stream(values())
                .filter(value -> value.matchRegex(url))
                .findAny()
                .orElse(ERROR_CONTROLLER);
    }

    private boolean matchRegex(Url url) {
        for (String regex :regexes) {
            if(url.getUrl().contains(regex))
                return true;
        }
        return false;
    }


}
