package ProjectHub.ECE.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ProjectHub.ECE.dao.UserDao;
import ProjectHub.ECE.model.User;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void register(User user) {
        userDao.save(user);
    }

    public User login(String email, String password) {

        User user = userDao.findByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            return user;
        }

        return null;
    }

    public List<User> getAllStudents() {
        return userDao.findAllStudents();
    }
    public User getUserByEmail(String email) {
    return userDao.findByEmail(email);
    }
}