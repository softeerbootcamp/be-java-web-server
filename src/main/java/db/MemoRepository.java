package db;

import model.Memo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class MemoRepository {
    private static final Database DATABASE = Database.getInstance();

    public static void addMemo(Memo memo) {
        String SQL = "insert into memo(username, createdAt, content) values(?, ?, ?)";
        try(Connection conn = DATABASE.getConnection(); PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, memo.getUserName());
            pstmt.setString(2, memo.getCreatedAt());
            pstmt.setString(3, memo.getContent());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Collection<Memo> findAll(){
        Collection<Memo> memos = new ArrayList<>();
        String SQL = "select * from memo";
        try(Connection conn = DATABASE.getConnection(); PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String username = rs.getString("username");
                String createdAt = rs.getString("createdAt");
                String content = rs.getString("content");
                memos.add(Memo.from(username, createdAt, content));
            }
            return memos;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
