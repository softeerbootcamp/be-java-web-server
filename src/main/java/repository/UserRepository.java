package repository;

import com.google.common.collect.Lists;
import db.DBUtil;
import exception.DBException;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static model.User.*;

public class UserRepository {

    public void save(User user) {
        Connection con = null;
        PreparedStatement pstm = null;
        String sql = "INSERT INTO User(login_id, password, name, email) VALUES(?, ?, ?, ?)";
        try {
            con = DBUtil.getConnection();
            pstm = con.prepareStatement(sql);

            pstm.setString(1, user.getLoginId());
            pstm.setString(2, user.getPassword());
            pstm.setString(3, user.getName());
            pstm.setString(4, user.getEmail());

            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e);
        } finally {
            DBUtil.close(con, pstm, null);
        }
    }

    public Optional<User> findById(Long id) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        User user = null;
        String sql = "SELECT * FROM User WHERE id = (?)";
        try {
            con = DBUtil.getConnection();
            pstm = con.prepareStatement(sql);
            pstm.setLong(1, id);
            rs = pstm.executeQuery();
            if (rs.next()) {
                user = User.of(
                        rs.getLong(ID),
                        rs.getString(LOGIN_ID),
                        rs.getString(PASSWORD),
                        rs.getString(NAME),
                        rs.getString(EMAIL)
                );
            }
        } catch (SQLException e) {
            throw new DBException(e);
        } finally {
            DBUtil.close(con, pstm, rs);
            return Optional.ofNullable(user);
        }
    }

    public Optional<User> findByLoginId(String loginId) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        User user = null;
        String sql = "SELECT * FROM User WHERE login_id LIKE ?";
        try {
            con = DBUtil.getConnection();
            pstm = con.prepareStatement(sql);
            pstm.setString(1, loginId);
            rs = pstm.executeQuery();
            if (rs.next()) {
                user = User.of(
                        rs.getLong(ID),
                        rs.getString(LOGIN_ID),
                        rs.getString(PASSWORD),
                        rs.getString(NAME),
                        rs.getString(EMAIL)
                );
            }
        } catch (SQLException e) {
            throw new DBException(e);
        } finally {
            DBUtil.close(con, pstm, rs);
            return Optional.ofNullable(user);
        }
    }

    public List<User> findAll() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<User> users = Lists.newArrayList();
        String sql = "SELECT * FROM User";
        try {
            con = DBUtil.getConnection();
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                users.add(User.of(
                        rs.getLong(ID),
                        rs.getString(LOGIN_ID),
                        rs.getString(PASSWORD),
                        rs.getString(NAME),
                        rs.getString(EMAIL)
                ));
            }
        } catch (SQLException e) {
            throw new DBException(e);
        } finally {
            DBUtil.close(con, pstm, rs);
            return users;
        }
    }
}
