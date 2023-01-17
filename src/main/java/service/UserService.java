package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.UserReposioryImpl;
import db.UserRepository;
import model.User;

public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	private static UserService instance;
	private final UserRepository userRepository;

	public static UserService getInstance() {
		if (instance == null) {
			synchronized (UserService.class) {
				instance = new UserService();
			}
		}
		return instance;
	}

	public UserService() {
		this.userRepository = UserReposioryImpl.getInstance();
	}

	public boolean matchIdPassword(String userId, String password) {
		// id가 존재하지 않는 경우
		if (!idExist(userId)) {
			return false;
		}
		return userRepository.findUserById(userId).matchPassword(password);
	}

	public boolean idExist(String userId) {
		return userRepository.idExist(userId);
	}

	public void addUser(String userId, String password, String name, String email) {
		User user = User.of(userId, password, name, email);
		userRepository.addUser(user);
		logger.info(user + " 회원가입했습니다.");
	}

	public User findByUserId(String userId) {
		return userRepository.findUserById(userId);
	}
}
