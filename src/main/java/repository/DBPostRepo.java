package repository;

import model.Post;
import model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class DBPostRepo implements PostRepo {

    private static DBPostRepo instance;

    public static DBPostRepo get(){
        if(instance == null)
            instance = new DBPostRepo();
        return instance;
    }

    public Post addPost(Post post){
        String[] arguments = {"0", post.getAuthor(), post.getTitle(), post.getContents()};
        DBConnectionManager.sendSql("insert into Post values (?, ?, ?, ?)", arguments);

        List<List<String>> result = DBConnectionManager.sendSql("SELECT LAST_INSERT_ID();", null);
        post.setPostId(Long.parseLong(result.get(0).get(0)));
        return post;
    }
    public Optional<Post> findPostById(Long postId){
        String[] arguments = {postId.toString()};
        List<List<String>> result = DBConnectionManager.sendSql("select id, author, title, contents from Post where id=?", arguments);
        if(result.size() == 0)
            return Optional.empty();
        return Optional.of(new Post(result.get(0)));
    }
    public Collection<Post> findAll(){
        List<List<String>> result = DBConnectionManager.sendSql("select id, author, title, contents from Post", null);
        List<Post> posts = new ArrayList<>();
        for(List<String> row : result){
            posts.add(new Post(row));
        }
        return posts;
    }

    public void delete(Post post){
        String[] args = {post.getPostId().toString()};
        DBConnectionManager.sendSql("delete from Post where id=?", args);
    }
}
