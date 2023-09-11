package br.com.uanderson.security.interfaces;

import br.com.uanderson.security.model.Role;
import br.com.uanderson.security.model.User;

public interface UserServiceInterface {
    User saveUser(User user);
    Role saveRole(Role role);
    void addToUser(String username, String roleName);
}
