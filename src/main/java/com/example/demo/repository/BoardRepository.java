package com.example.demo.repository;

import com.example.demo.model.Board;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;

@Slf4j
public class BoardRepository {

    private static final String url = "jdbc:mysql://localhost:3306/webserver";
    private static final String id = "root";
    private static final String password = "1234";
    private Connection con = null;
    private PreparedStatement pstt = null;
    private java.sql.Statement st = null;
    private ResultSet resultSet = null;

    public BoardRepository() {
        try {
            con = DriverManager.getConnection(url, id, password);
            st = con.createStatement();
        }
        catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
        }
    }

    public void insert(Board board) {
        String sql = "INSERT INTO board VALUES (?,?,?)";

        try{
            pstt = con.prepareStatement(sql);
            pstt.setString(1,board.getWriter());
            pstt.setString(2,board.getTitle());
            pstt.setString(3,board.getContent());
            pstt.executeUpdate();
            log.debug("새로운 레코드를 추가하였습니다 : {}",board.getWriter());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Board findByBoardId(String writer) {
        String sql = "SELECT * FROM board WHERE writer = ?";
        try{
            pstt = con.prepareStatement(sql);
            pstt.setString(1,writer);

            resultSet = pstt.executeQuery();

            if (resultSet.next()) {
                Board board = new Board(
                        resultSet.getString("writer"),
                        resultSet.getString("title"),
                        resultSet.getString("content")
                );
                return board;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
