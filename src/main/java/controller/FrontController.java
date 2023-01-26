package controller;

import view.Model;
import view.ViewHandler;
import request.HttpRequest;
import response.HttpResponse;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Map;

public class FrontController {

    private static final Map<String,Controller> controllers;
    private static final String SIGN_UP_PATH_URL = "/user/create";
    private static final String LOGIN_PATH_URL ="/user/login";
    private static final String USER_LIST_PATH_URL ="/user/list";
    private static final String QNA_FORM_PATH ="/qna/form.html";
    private static final String QNA_DETAIL_PATH ="/qna/show.html";

    //객체 캐싱
    static{
        SignUpController signUpController = SignUpController.getInstance();
        LoginController loginInController = LoginController.getInstance();
        UserListController userListController = UserListController.getInstance();
        QnaController qnaController = QnaController.getInstance();
        QnaShowController qnaShowController = QnaShowController.getInstance();
        controllers = Map.of(
                SIGN_UP_PATH_URL,signUpController,
                LOGIN_PATH_URL,loginInController,
                USER_LIST_PATH_URL,userListController,
                QNA_FORM_PATH,qnaController,
                QNA_DETAIL_PATH,qnaShowController
        );
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
            //해당 요청에 대해 찾은 컨트롤러에서 doGet, doPost로 분기되어 처리되도록 구현했음
            //그런데 현재 과제에서의 url은 get과 post 요청 url들이 각각 다르다는 점 기억하기
            Controller controller = controllers.get(url);
            // 인덱스 파일 요청에 대해서 처리하기 위해 별도로 만든 것
            if(controller == null){
                controller = FileController.getInstance();
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
