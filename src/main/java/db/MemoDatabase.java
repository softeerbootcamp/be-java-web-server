package db;

import model.Memo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemoDatabase {
    private static final Logger logger = LoggerFactory.getLogger(UserDatabase.class);

    public static void addMemo(Memo memo) {
        try {
            Connection conn = DbConnectionManager.getConnection();
            String sql = "insert into Memo(writer, content, createdAt) values(?,?,?)";
            PreparedStatement psmt = conn.prepareStatement(sql);

            psmt.setString(1, memo.getWriter());
            psmt.setString(2, memo.getContent());
            psmt.setTimestamp(3, Timestamp.valueOf(memo.getCreatedAt()));
            psmt.executeUpdate();

            logger.debug("Memo: {}", memo);

            psmt.close();
            conn.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    public static List<Memo> findAll() {
        List<Memo> memos = new ArrayList<>();

        try {
            Connection connection = DbConnectionManager.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("select * from memo;");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                memos.add(0, Memo.of(resultSet.getLong("memoId"),
                        resultSet.getString("writer"),
                        resultSet.getString("content"),
                        resultSet.getTimestamp("createdAt").toLocalDateTime()));
            }

            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return memos;
    }
}
