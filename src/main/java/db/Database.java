package db;

import com.google.common.collect.Maps;
import http.common.Session;
import model.Post;
import model.User;

import java.util.Collection;
import java.util.Map;

public class Database {
    private static Map<String, User> users = Maps.newHashMap();
    private static Map<String, Session> sessions = Maps.newHashMap();
    private static Map<Long, Post> posts = Maps.newHashMap();
    private static Long POST_SEQUENCE = 0l;

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static void deleteUser(String Userid) {
        users.remove(Userid);
    }

    public static User findUserById(String userId) {
        return users.get(userId);
    }

    public static Collection<User> findAll() {
        return users.values();
    }

    public static void addSession(Session session) {
        sessions.put(session.getId(), session);
    }

    public static Session getSession(String sid) {
        return sessions.get(sid);
    }

    public static void savePost(Post post) {
        post.setId(++POST_SEQUENCE);
        posts.put(post.getId(), post);
    }
}
