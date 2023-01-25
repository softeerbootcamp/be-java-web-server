package repository;

import com.google.common.collect.Maps;
import model.User;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class MemoryUserRepo implements UserRepo{
    private static MemoryUserRepo instance;
    private final Map<String, User> users = Maps.newHashMap();

    private MemoryUserRepo() {
    }

    public static MemoryUserRepo getInstance(){
        if(instance == null){
            instance = new MemoryUserRepo();
        }
        return instance;
    }
    
    public void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public Optional<User> findUserById(String userId) {
        return Optional.ofNullable(users.get(userId));
    }

    public Collection<User> findAll() {
        return users.values();
    }
}
