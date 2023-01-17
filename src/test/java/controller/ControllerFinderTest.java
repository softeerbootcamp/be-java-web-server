package controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import request.RequestDataType;
import request.Url;

import static org.assertj.core.api.Assertions.*;

class ControllerFinderTest {

    private final String fileUrl = "/index.html";
    private final String userUrl = "/user/any";
    private final String notValidUrl = "/dksldlrjdho";

    @Test
    @DisplayName("[factoryController]fileType찾기")
    void factoryControllerFindFileController() {
        //given
        Url url = new Url(fileUrl, RequestDataType.TEMPLATES_FILE);
        //when
        Controller controller = ControllerFinder.findController(url);
        //then
        assertThat(controller).isInstanceOf(FileController.class);
    }

    @Test
    @DisplayName("[factoryController]UserController찾기")
    void factoryControllerFindUserController() {
        //given
        Url url = new Url(userUrl,RequestDataType.IN_BODY);
        //when
        Controller controller = ControllerFinder.findController(url);
        //then
        assertThat(controller).isInstanceOf(UserController.class);
    }

//    @Test
//    @DisplayName("[factoryController]errorController 찾기")
//    void factoryControllerFindErrorController() {
//        //given
//        Url url = new Url(notValidUrl,RequestDataType.IN_BODY);
//        //when
//        Controller controller = ControllerFinder.findController(url);
//        //then
//        assertThat(controller).isInstanceOf(ErrorController.class);
//    }


}