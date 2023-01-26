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

        homeStr = homeStr.replace("<!--author-->",
                board.getWriter());
        homeStr = homeStr.replace("<!--createTime-->",
                board.getCreateTime().toString());
        homeStr = homeStr.replace("<!--content-->",
                board.getContent());
        homeStr = homeStr.replace("<!--title-->",
                board.getTitle());
        return homeStr.getBytes();
    }

}