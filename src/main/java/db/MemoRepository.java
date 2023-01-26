package db;

import model.Memo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

public class MemoRepository {
    private static final Database DATABASE = Database.getInstance();

    public static void addMemo(Memo memo) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DATABASE.getConnection();
            String SQL = "insert into memo(username, createdAt, content) values(?, ?, ?)";
            pstmt = conn.prepareStatement(SQL);

            pstmt.setString(1, memo.getUserName());
            pstmt.setString(2, memo.getCreatedAt());
            pstmt.setString(3, memo.getContent());
            pstmt.executeUpdate();

        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static Collection<Memo> findAll() throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        Collection<Memo> memos = new ArrayList<>();
        try {
            conn = DATABASE.getConnection();
            String SQL = "select * from memo";
            pstmt = conn.prepareStatement(SQL);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String username = rs.getString("username");
                String createdAt = rs.getString("createdAt");
                String content = rs.getString("content");
                memos.add(Memo.from(username, createdAt, content));
            }

        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return memos;
    }
}
