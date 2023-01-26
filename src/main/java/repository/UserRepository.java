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
        String sql = "INSERT INTO User(login_id, password, name, email) VALUES(?, ?, ?, ?)";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.getLoginId());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setString(4, user.getEmail());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM User WHERE id = (?)";
        User user = null;
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = User.of(
                        rs.getLong(ID),
                        rs.getString(LOGIN_ID),
                        rs.getString(PASSWORD),
                        rs.getString(NAME),
                        rs.getString(EMAIL)
                );
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public Optional<User> findByLoginId(String loginId) {
        String sql = "SELECT * FROM User WHERE login_id LIKE ?";
        User user = null;
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, loginId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = User.of(
                        rs.getLong(ID),
                        rs.getString(LOGIN_ID),
                        rs.getString(PASSWORD),
                        rs.getString(NAME),
                        rs.getString(EMAIL)
                );
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public List<User> findAll() {
        List<User> users = Lists.newArrayList();
        String sql = "SELECT * FROM User";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                users.add(User.of(
                        rs.getLong(ID),
                        rs.getString(LOGIN_ID),
                        rs.getString(PASSWORD),
                        rs.getString(NAME),
                        rs.getString(EMAIL)
                ));
            }
            return users;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
}
