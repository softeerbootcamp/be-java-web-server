package request.handlers;

import file.FileContentType;
import request.Request;
import request.RequestHandler;
import response.HttpResponseStatus;
import response.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;

public class IndexHandler implements RequestHandler {
    private static final IndexHandler instance;

    static {
        instance = new IndexHandler();
    }

    private IndexHandler() {}

    public static IndexHandler getInstance() {
        return instance;
    }

    @Override
    public Response doGet(Request request) {
        try {
            String resource = request.getResource();
            if(!resource.endsWith(".html")) {
                resource += ".html";
            }
            // TODO: 경로의 파일을 바이트로 넘겨주고 메소드 안에서 다시 String으로 변환하는 구조 수정
            byte[] file = generateDynamicHeader(request.getCookie(),
                    Files.readAllBytes(new File("src/main/resources/templates" + resource).toPath()));
            return Response.createSimpleResponse(file, FileContentType.HTML.getContentType(), HttpResponseStatus.OK);
        } catch (IOException e) {
            return Response.from(HttpResponseStatus.NOT_FOUND);
        } catch (SQLException | NullPointerException e) {
            return  Response.from(HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
