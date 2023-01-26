package db;

import com.google.common.collect.Maps;
import model.Article;
import webserver.DatabaseConnHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class BoardDatabase {
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

    // todo : 보안 취약? 전부 들고오는것 조금....
    public static Collection<Article> getAllArticles() throws SQLException {
        Collection<Article> articleCollection = new ArrayList<>();
        conn = databaseConnHandler.dbConnection();
        String sql = "SELECT * FROM Board";
        PreparedStatement psmt = conn.prepareStatement(sql);
        ResultSet rs = psmt.executeQuery();
        while (rs.next()) {
            articleCollection.add(
                    new Article(
                            rs.getString("Date"),
                            rs.getString("ID"),
                            rs.getString("Body")));
        }
        conn.close();
        return articleCollection;
    }

}
