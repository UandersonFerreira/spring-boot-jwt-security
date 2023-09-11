package br.com.uanderson.security.service;

import br.com.uanderson.security.model.User;
import br.com.uanderson.security.repository.RoleCustomRepository;
import br.com.uanderson.security.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.xml.crypto.AlgorithmMethod;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Service@RequiredArgsConstructor
public class JwtService {
    @Value("${secret.key}")
    private String secretKey;

    private final UserRepository userRepository;
    private final RoleCustomRepository roleCustomRepository;


    public String generateToken(User user, Collection<SimpleGrantedAuthority> authorities){
        Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
        return JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + (50*60*1000)))
                .withClaim("roles", authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
    }

    public String generateRefreshToken(User user, Collection<SimpleGrantedAuthority> authorities){
        Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
        return JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + (70*60*1000)))
                .sign(algorithm);
    }


}
