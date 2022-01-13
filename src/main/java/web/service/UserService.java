package web.service;

import web.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    public User getUserByUsername(String username);
    public List<User> allUsers();
    public void add(User user);
    public void edit(User user);
    public void delete(Integer id);
    public UserDetails loadUserByUsername(String username);
}
