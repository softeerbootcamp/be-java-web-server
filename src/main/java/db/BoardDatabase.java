package db;

import com.google.common.collect.Maps;
import model.Article;
import webserver.DatabaseConnHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class BoardDatabase {
    private static Map<Integer, Article> boards = Maps.newHashMap();
    private static DatabaseConnHandler databaseConnHandler;
    private static Connection conn;
    public static void addArticleToBoard(Article article) throws SQLException {
        conn = databaseConnHandler.dbConnection();
        String sql = "insert into Board values(?,?,?)";
        PreparedStatement psmt = conn.prepareStatement(sql);
        psmt.setString(1, article.getDate());
        psmt.setString(2, article.getUserId());
        psmt.setString(3, article.getBody());
        psmt.executeUpdate();
        conn.close();
    }

}
