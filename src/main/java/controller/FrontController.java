package controller;

import service.SignUpService;
import view.Model;
import view.ViewHandler;
import request.HttpRequest;
import response.HttpResponse;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class FrontController {
    private static final Map<String,Controller> controllers;
    private static final String SIGN_UP_PATH_URL = "/user/create";
    private static final String LOGIN_PATH_URL ="/user/login";
    private static final String USER_LIST_PATH_URL ="/user/list";
    private static final String GENERAL_FILE_PATH ="/file";

    //객체 캐싱
    static{
        SignUpService signUpService = new SignUpService();
        SignUpController signUpController = new SignUpController(signUpService);
        LoginController loginInController = new LoginController();
        UserListController userListController = new UserListController();
        FileController fileController = new FileController();
        controllers = Map.of(
                SIGN_UP_PATH_URL,signUpController,
                LOGIN_PATH_URL,loginInController,
                USER_LIST_PATH_URL,userListController,
                GENERAL_FILE_PATH,fileController
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
    public void handle(HttpRequest request, HttpResponse response) {
        try{
            String url = request.getUrl();
            Model model = new Model();
            Controller controller = controllers.get(url);
            if(controller == null){
                controller = new FileController();
            }
            String path = controller.service(request,response,model);
            if(path.startsWith("./templates")){// ./templates/index.html ,템플릿 하위에 있는 애들은 뷰로 그려줘야 함
                ViewHandler.handle(path,request,response,model);
            }
        }catch(NullPointerException e){
            NotFoundExceptionHandler.showErrorPage(response);
             e.printStackTrace();
            System.out.println("해당 url에 대한 응답이 없습니다");
        } catch (FileNotFoundException e) {
            NotFoundExceptionHandler.showErrorPage(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }catch(Exception e){
            NotFoundExceptionHandler.showErrorPage(response);
        }
    }
}
