package db.mysql;

import db.UserIdSession;
import model.Article;
import model.User;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class DB_Board {
    private static final String JDBC_URI = "jdbc:mysql://localhost/Java_was";
    private static final String ADMIN_ID = "admin";
    private static final String ADMIN_PASSWORD = "admin1234";

    private static Connection conn = null;
    private static PreparedStatement pstmt = null;
    private static ResultSet rs = null;

    public static void addArticle(String article, String sid_uid) throws SQLException {
        String userId = UserIdSession.getUserId(sid_uid);
        Calendar currCal = Calendar.getInstance();

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(JDBC_URI + "?" +
                    "user=" + ADMIN_ID + "&" +
                    "password=" + ADMIN_PASSWORD);

            // insert
            String sql = "INSERT INTO board(writerId, content, date) VALUES ("
                       +    "(SELECT id FROM Users where uid = ?)"
                       + ", ?, ?)";
            // TODO foreign key 제약조건에 걸리지 않고 넣는 방법 고안하기

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            pstmt.setString(2, article);
            pstmt.setString(3,
                         currCal.get(Calendar.YEAR) + "-" +
                            new Integer(currCal.get(Calendar.MONTH)+1).toString() + "-"+
                            currCal.get(Calendar.DAY_OF_MONTH)
            );

            pstmt.executeUpdate();
        }catch (SQLException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            pstmt.close();
            conn.close();
        }
    }
    public static Collection<Article> findAll() throws SQLException {
        Collection<Article> allArticles = new ArrayList<>();
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(JDBC_URI + "?" +
                    "user=" + ADMIN_ID + "&" +
                    "password=" + ADMIN_PASSWORD);

            String sql = "SELECT uid, content, date " +
                                "FROM users RIGHT OUTER JOIN board " +
                                "ON users.id = board.writerId ORDER BY board.id DESC";
            // 탈퇴한 유저의 글은 디비에 남아있음.
            // 구현은 안했지만, 탈퇴한 유저의 (작성자 ID)를 바꾸는 기능을 위해 right outer join으로 남겨놓음.
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();
            int addNum = 0;
            while(rs.next())
            {
                allArticles.add(
                        new Article(
                                rs.getString("uid"),
                                rs.getString("content"),
                                rs.getString("date")
                        )
                );
                addNum++;
                if(addNum >= 5) break;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            pstmt.close();
            conn.close();
        }

        return allArticles;
    }
}
