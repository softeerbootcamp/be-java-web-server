package view;

import model.Board;

public class BoardRender {

    private static BoardRender boardRender;

    private BoardRender() {
    }

    public static BoardRender getInstance() {
        if (boardRender == null) {
            boardRender = new BoardRender();
        }
        return boardRender;
    }

    public byte[] addBoardInfo(byte[] homeData, Board board) {
        String homeStr = new String(homeData);
        homeStr = homeStr.replace("<!--                <li><a href=\"user/login.html\" role=\"button\">로그인</a></li>-->",
                "                <li><a href=\"user/login.html\" role=\"button\">로그인</a></li>");
        homeStr = homeStr.replace("<!--                <li><a href=\"user/form.html\" role=\"button\">회원가입</a></li>-->",
                "                <li><a href=\"user/form.html\" role=\"button\">회원가입</a></li>");
        return homeStr.getBytes();
    }

}