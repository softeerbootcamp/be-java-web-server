package db.jdbc;

import db.tmpl.ConnectionManager;
import db.tmpl.SessionDatabase;
import model.Board;
import model.HttpSession;
import org.springframework.stereotype.Component;
import webserver.utils.CommonUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static webserver.utils.CommonUtils.strToLocalDateTime;

@Component
public class JdbcSessionDatabase implements SessionDatabase {

    private ConnectionManager connectionManager;
    private JdbcSessionDatabase(){}

    public static JdbcSessionDatabase getInstance(){
        return JdbcSessionDatabase.LazyHolder.INSTANCE;
    }

    private static class LazyHolder{
        private static final JdbcSessionDatabase INSTANCE = new JdbcSessionDatabase();
    }

    public void setConnectionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public void addCookie(HttpSession session) {
        try(Connection conn = connectionManager.makeConnection(); PreparedStatement ps = conn.prepareStatement("insert into session(sessionId, userId, username, createDate, expireDate, lastAccessDate, path, maxAge) values (?,?,?,?,?,?,?,?)")) {
            ps.setString(1, session.getSessionId());
            ps.setString(2, session.getUserId());
            ps.setString(3, session.getUsername());
            ps.setString(4, session.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:MM")));
            ps.setString(5, session.getExpireDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:MM")));
            ps.setString(6, session.getLastAccessDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:MM")));
            ps.setString(7, session.getPath());
            ps.setInt(8, session.getMaxAge());
            ps.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Optional<HttpSession> findSessionById(String sessionId)  {
        try(Connection conn = connectionManager.makeConnection(); PreparedStatement ps = conn.prepareStatement("select * from session where sessionId = ?")) {
            ps.setString(1, sessionId);

            ResultSet rs = ps.executeQuery();
            if (!rs.isBeforeFirst())
                return Optional.empty();
            rs.next();
            HttpSession session = HttpSession.builder()
                    .sessionId(rs.getString("sessionId"))
                    .userId(rs.getString("userId"))
                    .username(rs.getString("username"))
                    .createDate(strToLocalDateTime(rs.getString("createDate")))
                    .expireDate(strToLocalDateTime(rs.getString("expireDate")))
                    .lastAccessDate(strToLocalDateTime(rs.getString("lastAccessDate")))
                    .path(rs.getString("path"))
                    .maxAge(rs.getInt("maxAge"))
                    .build();
            return Optional.of(session);
        }catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void deleteCookie(String sessionId){
        try(Connection conn = connectionManager.makeConnection(); PreparedStatement ps = conn.prepareStatement("delete from session where sessionId = ?")) {
            ps.setString(1, sessionId);
            ps.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<HttpSession> findAll() {
        try(Connection conn = connectionManager.makeConnection(); PreparedStatement ps = conn.prepareStatement("select * from session")){
            List<HttpSession> sessionList = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                HttpSession session = HttpSession.builder()
                        .sessionId(rs.getString("boardId"))
                        .userId(rs.getString("writerId"))
                        .username(rs.getString("writerName"))
                        .createDate(strToLocalDateTime(rs.getString("createDate")))
                        .expireDate(strToLocalDateTime(rs.getString("expireDate")))
                        .lastAccessDate(strToLocalDateTime(rs.getString("lastAccessDate")))
                        .path(rs.getString("path"))
                        .maxAge(rs.getInt("maxAge"))
                        .build();
                sessionList.add(session);
            }
            return sessionList;
        }catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
