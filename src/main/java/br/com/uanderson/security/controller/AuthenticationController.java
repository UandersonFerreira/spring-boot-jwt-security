package br.com.uanderson.security.controller;

import br.com.uanderson.security.auth.AuthenticationRequest;
import br.com.uanderson.security.auth.RegisterRequest;
import br.com.uanderson.security.model.User;
import br.com.uanderson.security.repository.UserRepository;
import br.com.uanderson.security.service.AuthenticationService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest){
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @GetMapping
    public List<User> listAll(){
        return userRepository.findAll();
    }


}//class
