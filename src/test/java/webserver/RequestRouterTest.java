package webserver;

import httpMock.CustomHttpErrorFactory;
import httpMock.CustomHttpRequest;
import httpMock.CustomHttpResponse;
import httpMock.constants.ContentType;
import httpMock.constants.StatusCode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.StaticFileService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestRouterTest {
    static RequestRouter requestRouter;

    @BeforeAll
    static void setRequestRouter() {
        requestRouter = RequestRouter.getRequestRouter();
    }

    @Test
    void Index_html_요청_확인() throws IOException {
        //given
        CustomHttpRequest req = CustomHttpRequest.of(
                "GET /index.html HTTP/1.1",
                Collections.EMPTY_LIST,
                Collections.EMPTY_LIST
        );
        CustomHttpResponse expected = CustomHttpResponse.of(
                StatusCode.OK,
                ContentType.TEXT_HTML,
                new HashMap<>(),
                Files.readAllBytes(Path.of(StaticFileService.getFile("/index.html").getPath()))
        );

        //when
        CustomHttpResponse res = requestRouter.handleRequest(req);


        //then
        assertEquals(res.toString(), expected.toString());
    }

    @Test
    void 루트주소_요청확인() throws IOException {
        //given
        CustomHttpRequest req = CustomHttpRequest.of(
                "GET / HTTP/1.1",
                Collections.EMPTY_LIST,
                Collections.EMPTY_LIST
        );
        CustomHttpResponse expected = new CustomHttpResponse(
                StatusCode.OK,
                ContentType.TEXT_HTML,
                new HashMap<>(),
                Files.readAllBytes(Path.of(StaticFileService.getFile("/index.html").getPath()))
        );

        //when
        CustomHttpResponse res = requestRouter.handleRequest(req);

        //then
        assertEquals(res.toString(), expected.toString());
    }

    @Test
    void 회원가입_리다이렉트확인() {
        //given
        CustomHttpRequest req = CustomHttpRequest.of(
                "POST /user/create HTTP/1.1",
                Collections.EMPTY_LIST,
                List.of("userId=rohsik2&password=qwevcqvew&name=adfqewfrw&email=rohsik@gmail.com")
        );

        CustomHttpResponse expected = new CustomHttpResponse(
                StatusCode.FOUND,
                ContentType.TEXT_PLAIN,
                new HashMap<>() {{
                    put("Location", "/index.html");
                }},
                CustomHttpResponse.EMPTY_BODY
        );

        //when
        CustomHttpResponse response = requestRouter.handleRequest(req);

        //then
        assertEquals("/index.html", response.getHeaders().get("Location"));
        assertEquals(expected.toString(), response.toString());

    }

    @Test
    void 없는주소_요청처리() {
        //given
        CustomHttpRequest req = CustomHttpRequest.of(
                "GET /asdrqweboq HTTP/1.1",
                Collections.EMPTY_LIST,
                Collections.EMPTY_LIST
        );

        CustomHttpResponse expected = new CustomHttpResponse(
                StatusCode.NOT_FOUND,
                ContentType.TEXT_PLAIN,
                new HashMap<>(),
                CustomHttpResponse.EMPTY_BODY
        );

        //when
        CustomHttpResponse response = requestRouter.handleRequest(req);

        //then
        assertEquals(expected.toString(), response.toString());
    }

    @Test
    void 없는파일_요청처리() {
        //given
        CustomHttpRequest req = CustomHttpRequest.of(
                "GET /hellmynameisrohyunuk.html HTTP/1.1",
                Collections.EMPTY_LIST,
                Collections.EMPTY_LIST
        );

        //when
        CustomHttpResponse res = requestRouter.handleRequest(req);

        //then
        assertEquals(CustomHttpErrorFactory.NOT_FOUND().toString(), res.toString());
    }

}