package db;

import model.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class SessionDatabase {
    private static final Logger logger = LoggerFactory.getLogger(SessionDatabase.class);

    private static final SessionDatabase instance;

    private SessionDatabase() {}

    static {
        instance = new SessionDatabase();
    }

    public static SessionDatabase getInstance() {
        return instance;
    }

    // TODO: QueryExecutor 적용하기
    public void add(Session session) throws SQLException, NullPointerException {
        Connection con = DatabaseConnection.getConnection();
        try {
            String query = "INSERT INTO Sessions (id, expirationTime, userId) VALUES(?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, session.getSid());
            pstmt.setString(2, session.getExpirationTime().toString());
            pstmt.setString(3, session.getUserId());
            pstmt.executeUpdate();

            con.close();
            logger.debug("session {} added", session.getUserId());
        } finally {
            con.close();
        }
    }

    public Optional<Session> findById(String sid) throws SQLException, NullPointerException {
        Connection con = DatabaseConnection.getConnection();
        try {
            String query = "SELECT * FROM Sessions WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, sid);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                con.close();
                return Optional.empty();
            }

            String id = rs.getString("id");
            String userId = rs.getString("userId");

            con.close();
            return Optional.of(Session.of(id, userId));
        } finally {
            con.close();
        }
    }

    public boolean existsBySessionId(String sid) throws SQLException, NullPointerException {
        Connection con = DatabaseConnection.getConnection();
        try {
            String query = "SELECT * FROM Sessions WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, sid);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                con.close();
                return false;
            }

            con.close();
            return true;
        } finally {
            con.close();
        }
    }

    public void deleteSession(String sid) throws SQLException, NullPointerException {
        Connection con = DatabaseConnection.getConnection();
        try {
            String query = "DELETE FROM Sessions WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, sid);
            pstmt.executeUpdate();
        } finally {
            con.close();
        }
    }
}
