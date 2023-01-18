package model.repository;

import com.google.common.collect.Maps;

import controller.StaticController;
import model.domain.User;
import model.service.UserService;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class MemoryUserRepository implements UserRepository{
    private static Map<String, User> users = Maps.newHashMap();

    private static MemoryUserRepository memoryUserRepository;

    private MemoryUserRepository(){}

    public static UserRepository getInstance(){
        if (memoryUserRepository == null){
            synchronized (MemoryUserRepository.class){
                if (memoryUserRepository == null){
                    memoryUserRepository = new MemoryUserRepository();
                }
            }
        }
        return memoryUserRepository;
    }


    public User addUser(User user) {
        users.put(user.getUserId(), user);
        return user;
    }

    public Optional<User> findUserById(String userId) {
        return Optional.ofNullable(users.get(userId));
    }

    public Collection<User> findAll() {
        return users.values();
    }

    public void clearUsers(){
        users.clear();
    }
}
