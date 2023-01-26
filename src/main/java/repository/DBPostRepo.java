package repository;

import model.Post;

import java.util.*;

public class DBPostRepo implements PostRepo {

    private static DBPostRepo instance;

    public static DBPostRepo get(){
        if(instance == null)
            instance = new DBPostRepo();
        return instance;
    }

    public Post addPost(Post post){
        String[] arguments = {post.getAuthor(), post.getTitle(), post.getContents()};
        DBConnectionManager.sendSql("insert into Post(author, title, contents) values (?, ?, ?)", arguments);

        List<Map<String, String>> result = DBConnectionManager.sendSql("SELECT MAX(id) as id FROM Post;\n;", null);
        post.setPostId(Long.parseLong(result.get(0).get("id")));
        return post;
    }
    public Optional<Post> findPostById(Long postId){
        String[] arguments = {postId.toString()};
        List<Map<String, String>> result = DBConnectionManager.sendSql("select id, author, title, contents, created_at from Post where id=?", arguments);
        if(result.size() == 0)
            return Optional.empty();
        return Optional.of(new Post(result.get(0)));
    }
    public Collection<Post> findAll(){
        List<Map<String, String>> result = DBConnectionManager.sendSql("select id, author, title, contents, created_at from Post", null);
        List<Post> posts = new ArrayList<>();
        for(Map<String, String> row : result){
            posts.add(new Post(row));
        }
        return posts;
    }

    public void delete(Post post){
        String[] args = {post.getPostId().toString()};
        DBConnectionManager.sendSql("delete from Post where id=?", args);
    }
}
