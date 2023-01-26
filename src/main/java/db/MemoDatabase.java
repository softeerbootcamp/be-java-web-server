package db;

import com.google.common.collect.Maps;
import model.Memo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Map;

public class MemoDatabase {
    private static final Logger logger = LoggerFactory.getLogger(UserDatabase.class);
    private static final Map<Long, Memo> memos = Maps.newHashMap();

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

    public static Collection<Memo> findAll() {
        return memos.values();
    }
}
