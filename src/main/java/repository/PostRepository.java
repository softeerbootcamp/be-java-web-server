package repository;

import com.google.common.collect.Lists;
import dao.PostDAO;
import db.DBUtil;
import exception.DBException;
import model.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PostRepository {

    public void save(Post post) {
        String sql = "INSERT INTO Post(uid, title, content, createdDate) VALUES(?, ?, ?, ?)";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, post.getUser().getId());
            ps.setString(2, post.getTitle());
            ps.setString(3, post.getContent());
            ps.setString(4, post.getCreatedDate());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public List<PostDAO> getAllPosts() {
        String sql = "SELECT * FROM Post";
        List<PostDAO> posts = Lists.newArrayList();
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                posts.add(PostDAO.of(
                        rs.getLong("id"),
                        rs.getLong("uid"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getTimestamp("createdDate").toLocalDateTime()));
            }
            return posts;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public Optional<PostDAO> findById(long id) {
        String sql = "SELECT * FROM Post WHERE id = (?)";
        PostDAO postDAO = null;
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                postDAO = PostDAO.of(
                        rs.getLong("id"),
                        rs.getLong("uid"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getTimestamp("createdDate").toLocalDateTime()
                );
            }
            return Optional.ofNullable(postDAO);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
}
