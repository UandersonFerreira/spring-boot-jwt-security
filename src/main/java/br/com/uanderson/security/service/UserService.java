package br.com.uanderson.security.service;

import br.com.uanderson.security.interfaces.UserServiceInterface;
import br.com.uanderson.security.model.Role;
import br.com.uanderson.security.model.User;
import br.com.uanderson.security.repository.RoleRepository;
import br.com.uanderson.security.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService implements UserServiceInterface {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>());
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addToUser(String username, String roleName) {
        Optional<User> optionalUser = userRepository.findByEmail(username);
        Role role = roleRepository.findByName(roleName);
        if (optionalUser.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("User with email %s does not exits", username));
        }
        if (role == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Role with name %s does not exits", roleName));
        }
        User user = optionalUser.get();
        user.getRoles().add(role);
    }//method


}//class
