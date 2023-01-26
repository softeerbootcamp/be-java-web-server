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

    private static final String url = "jdbc:mysql://localhost:3306/board";
    private static final String id = "root";
    private static final String password = "1234";

    private Connection con = null;
    private PreparedStatement pstt = null;
    private java.sql.Statement st = null;
    private ResultSet result = null;

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
        Board board = new Board("park","sung","jun");
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

}
