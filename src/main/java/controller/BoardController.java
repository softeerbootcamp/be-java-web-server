package controller;

import controller.annotation.Auth;
import controller.annotation.ControllerInfo;
import controller.annotation.ControllerMethodInfo;
import db.BoardDataBase;
import model.Board;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reader.fileReader.FileReader;
import reader.fileReader.TemplatesFileReader;
import reader.requestReader.RequestGetReader;
import reader.requestReader.RequestPostReader;
import reader.requestReader.RequestReader;
import request.HttpRequest;
import request.RequestDataType;
import request.Url;
import response.Data;
import response.HttpResponse;
import util.Cookie;
import util.FileType;
import util.HttpMethod;
import util.HttpStatus;
import view.BoardRender;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ControllerInfo
public class BoardController implements Controller {
    private FileReader fileReader;

    private RequestReader requestReader;

    private BoardRender boardRender = BoardRender.getInstance();

    private BoardDataBase boardDataBase = BoardDataBase.getInstance();
    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

    @Auth
    @ControllerMethodInfo(path = "/qna/form.html", method = HttpMethod.GET)
    public HttpResponse qnaFormHtml(DataOutputStream dataOutputStream, HttpRequest httpRequest) throws IOException {
        fileReader = new TemplatesFileReader();
        byte[] data = fileReader.readFile(httpRequest.getUrl());
        return new HttpResponse.Builder()
                .setData(new Data(dataOutputStream, data))
                .setFileType(FileType.HTML)
                .setHttpStatus(HttpStatus.OK)
                .build();
    }


    @Auth
    @ControllerMethodInfo(path = "/qna/form", method = HttpMethod.POST)
    public HttpResponse insertBoard(DataOutputStream dataOutputStream, HttpRequest httpRequest) {
        RequestPostReader reader = new RequestPostReader();
        HashMap<String, String> dataMap = reader.readData(httpRequest);
        Board board = new Board(
                dataMap.get("writer"),
                dataMap.get("title"),
                dataMap.get("contents")
        );
        boardDataBase.insert(board);
        logger.debug("Board 저장: {]",board.toString());
        return new HttpResponse.Builder()
                .setData(new Data(dataOutputStream))
                .setFileType(FileType.HTML)
                .setHttpStatus(HttpStatus.RE_DIRECT)
                .setRedirectUrl(new Url("/index.html", RequestDataType.FILE))
                .build();
    }




    //PathVariable로 받음
    //TODO Pathvariable을 이용하여 Board객체 렌더링 진행중
    @ControllerMethodInfo(path = "^/qna/show/[0-9]+$", method = HttpMethod.GET)
    public HttpResponse boardShowHtml(DataOutputStream dataOutputStream, HttpRequest httpRequest) throws IOException {
        fileReader = new TemplatesFileReader();
        byte[] data = fileReader.readFile(httpRequest.getUrl());
        requestReader = new RequestGetReader();
        String boardId = requestReader.readData(httpRequest).get(RequestGetReader.PATH_VARIABLE_KEY);
        Board board = boardDataBase.findById(Long.valueOf(boardId));
        byte[] fixedData = boardRender.addBoardInfo(data, board);
        return new HttpResponse.Builder()
                .setData(new Data(dataOutputStream, fixedData))
                .setFileType(FileType.HTML)
                .setHttpStatus(HttpStatus.OK)
                .build();
    }
}
