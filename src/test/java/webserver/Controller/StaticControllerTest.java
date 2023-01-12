package webserver.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.domain.StatusCodes;
import webserver.domain.response.Response;
import webserver.utils.StaticResourceFinder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import static org.mockito.Mockito.*;

class StaticControllerTest {

    StaticController staticController;

    @BeforeEach
    void testSetUp(){
        staticController = new StaticController();
    }

    @Test
    @DisplayName("정적 리소스를 찾았을 때")
    public void ChainFound() throws IOException {

        //given
        StaticResourceFinder staticResourceFinder = mock(StaticResourceFinder.class);
        Response res = mock(Response.class);
        Map<String, String> queryString = new HashMap<>();
        String path = "file.txt";
        byte[] fileAsBytes = "file content".getBytes();

        //when
        when(staticResourceFinder.staticFileResolver(path)).thenReturn(Optional.of(fileAsBytes));

        //then
        staticController.chain(path, queryString, res);
        verify(staticResourceFinder).staticFileResolver(path);
        verify(res).ok(StatusCodes.OK, fileAsBytes, StaticResourceFinder.getExtension(path));
    }

    @Test
    public void testChainNotFound() throws IOException {

        //when
        StaticResourceFinder staticResourceFinder = mock(StaticResourceFinder.class);
        Response res = mock(Response.class);
        Map<String, String> queryString = new HashMap<>();
        String path = "file.txt";
        byte[] fileAsBytes = "file content".getBytes();

        //when
        when(staticResourceFinder.staticFileResolver(path)).thenReturn(Optional.empty());
        staticController.chain(path, queryString, res);

        //then
        verify(staticResourceFinder).staticFileResolver(path);
        verify(res).error(StatusCodes.NOT_FOUND);
    }
}