package controller;

import enums.ContentType;
import enums.HttpStatus;
import service.SignUpService;
import utils.FileIoUtils;
import view.Model;
import view.ViewHandler;
import request.HttpRequest;
import response.HttpResponse;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

public class FrontController {

    //TODO 현재 컨트롤러 별로 doGet빠진 부분 추가하고, user/list 제외하고 나머지 경로들 형식 통일하기
    private static final Map<String,Controller> controllers;
    private static final String SIGN_UP_PATH_URL = "/user/create";
    private static final String LOGIN_PATH_URL ="/user/login";
    private static final String USER_LIST_PATH_URL ="/user/list";
    private static final String QNA_FORM_PATH ="/qna/form.html";

    //객체 캐싱
    static{
        SignUpService signUpService = new SignUpService();
        SignUpController signUpController = new SignUpController(signUpService);
        LoginController loginInController = new LoginController();
        UserListController userListController = new UserListController();
        FileController fileController = new FileController();
        QnaController qnaController = new QnaController();
        controllers = Map.of(
                SIGN_UP_PATH_URL,signUpController,
                LOGIN_PATH_URL,loginInController,
                USER_LIST_PATH_URL,userListController,
                QNA_FORM_PATH,qnaController
        );
    }

    public Controller getControllerByUrl(String pathUrl){
        return controllers.get(pathUrl);
    }

    /**
     * 프론트 컨트롤러 : 요청 url에 따라 해당되는 컨트롤러를 찾고 해당 컨트롤러의 service를 실행
     * @param request
     * @param response
     */
    public void handle(HttpRequest request, HttpResponse response) throws FileNotFoundException, URISyntaxException {
        try{
            String url = request.getUrl();
            Model model = new Model();
            //해당 요청에 대해 찾은 컨트롤러에서 doGet, doPost로 분기되어 처리됨
            Controller controller = controllers.get(url);
            // 인덱스 파일에서 수많은 파일들 요청에 대해서 처리하기 위해 별도로 만든 것
            if(controller == null){
                controller = new FileController();
            }
            String path = controller.service(request,response,model);
            if(path.startsWith("./templates")){// ./templates/index.html ,템플릿 하위에 있는 애들은 뷰로 그려줘야 함
                ViewHandler.handle(path,request,response,model);
            }
        }catch(NullPointerException | FileNotFoundException | IllegalArgumentException e ){
            NotFoundExceptionHandler.showErrorPage(response);
            e.printStackTrace();
        }
    }
}
