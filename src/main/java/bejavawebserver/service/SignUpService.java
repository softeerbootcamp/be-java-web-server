package bejavawebserver.service;

import bejavawebserver.repository.MemoryRepository;
import bejavawebserver.model.User;
import org.springframework.stereotype.Service;

@Service
public class SignUpService {
    public static void addDatabase(User user) {
        if(MemoryRepository.checkDuplicate(user)) throw new RuntimeException("중복된 사용자가 있습니다.");
        MemoryRepository.addUser(user);
    }
}
