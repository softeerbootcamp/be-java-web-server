package webserver.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.domain.ContentType;
import webserver.view.ModelAndView;
import webserver.domain.StatusCodes;
import webserver.domain.request.Request;
import webserver.domain.response.Response;
import webserver.utils.StaticResourceFinder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import static org.mockito.Mockito.*;

class StaticControllerTest {

    StaticController staticController;
    Response res;
    Request req;
    ModelAndView mv;

    @BeforeEach
    void testSetUp(){
        staticController = StaticController.getInstance();
        String requestLine = "GET /index.html /1.1";
        String header = "";
        String body = "";
        req = Request.of(requestLine, header, body);
        req = mock(Request.class);
        mv = mock(ModelAndView.class);
    }

    @Test
    @DisplayName("정적 리소스를 찾았을 때")
    public void ChainFound() throws IOException {

        //given
        StaticResourceFinder staticResourceFinder = mock(StaticResourceFinder.class);
        Map<String, String> queryString = new HashMap<>();
        String path = "file.txt";
        byte[] fileAsBytes = "file content".getBytes();

        //when
        when(staticResourceFinder.staticFileResolver(path)).thenReturn(Optional.of(fileAsBytes));

        //then
        staticController.chain(req, res, mv);

        verify(res).addHeaderAndBody(StatusCodes.OK, fileAsBytes, StaticResourceFinder.getExtension(path));
    }

    @Test
    public void testChainNotFound() throws IOException {

        //when
        StaticResourceFinder staticResourceFinder = mock(StaticResourceFinder.class);
        Map<String, String> queryString = new HashMap<>();
        String path = "file.txt";
        byte[] fileAsBytes = "file content".getBytes();

        //when
        when(staticResourceFinder.staticFileResolver(path)).thenReturn(Optional.empty());
        staticController.chain(req, res, mv);

        //then
        verify(res).addHeaderAndBody(StatusCodes.NOT_FOUND, StatusCodes.NOT_FOUND.getStatusMsg().getBytes(), ContentType.TEXT_HTML);
    }
}