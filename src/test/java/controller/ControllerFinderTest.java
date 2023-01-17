package controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.Url;
import util.UrlType;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ControllerFinderTest {

    private final String fileUrl = "/index.html";
    private final String userUrl = "/user/any";
    private final String notValidUrl = "/dksldlrjdho";

    @Test
    @DisplayName("[factoryController]fileType찾기")
    void factoryControllerFindFileController() {
        //given
        Url url = new Url(fileUrl, UrlType.TEMPLATES_FILE);
        //when
        Controller controller = ControllerFinder.factoryController(url);
        //then
        assertThat(controller).isInstanceOf(FileController.class);
    }

    @Test
    @DisplayName("[factoryController]UserController찾기")
    void factoryControllerFindUserController() {
        //given
        Url url = new Url(userUrl, UrlType.QUERY_STRING);
        //when
        Controller controller = ControllerFinder.factoryController(url);
        //then
        assertThat(controller).isInstanceOf(UserController.class);
    }

    @Test
    @DisplayName("[factoryController]errorController 찾기")
    void factoryControllerFindErrorController() {
        //given
        Url url = new Url(notValidUrl, UrlType.QUERY_STRING);
        //when
        Controller controller = ControllerFinder.factoryController(url);
        //then
        assertThat(controller).isInstanceOf(ErrorController.class);
    }


}