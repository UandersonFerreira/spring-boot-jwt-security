package br.com.uanderson.security.service;

import br.com.uanderson.security.auth.AuthenticationRequest;
import br.com.uanderson.security.auth.AuthenticationResponse;
import br.com.uanderson.security.auth.RegisterRequest;
import br.com.uanderson.security.model.Role;
import br.com.uanderson.security.model.User;
import br.com.uanderson.security.repository.RoleCustomRepository;
import br.com.uanderson.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RoleCustomRepository roleCustomRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public ResponseEntity<?> authenticate(AuthenticationRequest authenticationRequest){
        try {

            User user = userRepository.findByEmail(authenticationRequest.getEmail())
                    .orElseThrow(() -> new NoSuchElementException("User not found"));

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(),
                    authenticationRequest.getPassword()));

            List<Role> roles = null;
            if (user != null) {
                roles = roleCustomRepository.getRole(user);
            }

            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            Set<Role> setRole = new HashSet<>();
            roles.forEach(role -> {
                setRole.add(new Role(role.getName()));
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            });
            user.setRoles(setRole);
            setRole.forEach(sr -> authorities.add(new SimpleGrantedAuthority(sr.getName())));

            String jwtAccessToken = jwtService.generateToken(user, authorities);
            String jwtRefreshToken = jwtService.generateRefreshToken(user, authorities);

            AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                    .accessToken(jwtAccessToken)
                    .refreshToken(jwtRefreshToken)
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .build();
           log.info("AuthenticationResponse -> {}", authenticationResponse.toString());
            return ResponseEntity.ok(authenticationResponse);
        }catch (NoSuchElementException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found | "+ex.getMessage());
        }catch (BadCredentialsException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Credential | "+ ex.getMessage());
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong to login | "+ ex.getMessage());
        }

    }//method

    public ResponseEntity<?> register(RegisterRequest registerRequest) {
        try {

            if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()){
                throw new IllegalArgumentException(String.format("User with '%s' email already exists!", registerRequest.getEmail()));
            }
            User userToBeSaved = User.builder()
                    .username(registerRequest.getUsername())
                    .email(registerRequest.getEmail())
                    .password(registerRequest.getPassword())
                    .mobileNumber(registerRequest.getMobileNumber())
                    .roles(new HashSet<>())
                    .build();
            userService.saveUser(userToBeSaved);
            userService.addToUser(registerRequest.getEmail(), "ROLE_USER");//default role
            User userSaved = userRepository.findByEmail(registerRequest.getEmail())
                    .orElseThrow(() -> new NoSuchElementException("User not found"));
            return ResponseEntity.ok(userSaved);
        }catch (IllegalArgumentException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Verifier de fields and try again | "+ex.getMessage());
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong to register| "+ ex.getMessage());
        }

    }
}//class
