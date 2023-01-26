package repository;

import model.User;

import java.util.*;

public class DBUserRepo implements UserRepo{
    @Override
    public void addUser(User user) {
        String[] args = {user.getUserId(), user.getPassword(), user.getName(), user.getEmail()};
        DBConnectionManager.sendSql("insert into Customer(id, password, name, email) values (?, ?, ?, ?)", args);
    }

    @Override
    public Optional<User> findUserById(String userId) {
        String[] args = {userId};
        List<Map<String, String>> users = DBConnectionManager.sendSql("select id, password, name, email from Customer where id=?", args);
        User user = null;
        if(users.size() > 0){
            user = new User(users.get(0));
        }
        return Optional.ofNullable(user);
    }

    @Override
    public Collection<User> findAll() {
        List<Map<String, String>> users = DBConnectionManager.sendSql("select id, password, name, email from Customer", null);
        List<User> userList = new ArrayList<>();
        for (Map<String,String> row : users) {
            User user = new User(row);
            userList.add(user);
        }
        return userList;
    }


    @Override
    public void delete(String userId) {
        String[] args = {userId};
        DBConnectionManager.sendSql("delete from Customer where id=?", args);
    }
}
