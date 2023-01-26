package com.example.demo.repository;

import com.example.demo.model.Board;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class boardRepositoryTest {

    private static final String url = "jdbc:mysql://localhost:3306/webserver";
    private static final String id = "root";
    private static final String password = "td134579";

    private Connection con = null;
    private PreparedStatement pstt = null;
    private java.sql.Statement st = null;
    private ResultSet resultSet = null;

    Board board = new Board("park","sung","jun");

    @Test
    void init() {
        try{
            pstt = null;
            con = DriverManager.getConnection(url, id, password);
        }
        catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
        }
    }

    @Test
    public void insert() {
        String sql = "INSERT INTO board VALUES (?,?,?)";
        try{

            con = DriverManager.getConnection(url, id, password);

            pstt = con.prepareStatement(sql);
            pstt.setString(1,board.getWriter());
            pstt.setString(2,board.getTitle());
            pstt.setString(3,board.getContent());
            pstt.executeUpdate();
            System.out.println("새로운 레코드를 추가하였습니다." + board.getWriter());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findByBoardId() {
        String writer = "park";

        String insertSql = "INSERT INTO board VALUES (?,?,?)";


        String sql = "SELECT * FROM board WHERE writer = ?";

        try{
            con = DriverManager.getConnection(url, id, password);

            //insert
            pstt = con.prepareStatement(insertSql);
            pstt.setString(1,board.getWriter());
            pstt.setString(2,board.getTitle());
            pstt.setString(3,board.getContent());
            pstt.executeUpdate();

            //select
            pstt = con.prepareStatement(sql);
            pstt.setString(1,writer);

            resultSet = pstt.executeQuery();

            if (resultSet.next()) {
                Board board2 = new Board(
                        resultSet.getString("writer"),
                        resultSet.getString("title"),
                        resultSet.getString("content")
                );
                System.out.println(board2.getWriter());
                System.out.println(board2.getTitle());
                System.out.println(board2.getContent());
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
