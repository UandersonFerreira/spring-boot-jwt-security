package br.com.uanderson.security;

import br.com.uanderson.security.model.Role;
import br.com.uanderson.security.model.User;
import br.com.uanderson.security.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@SpringBootApplication
@EnableWebSecurity
@EnableJpaRepositories
public class SpringBootJwtSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootJwtSecurityApplication.class, args);

    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner run(UserService userService) {
        return args -> {
            userService.saveRole(new Role(null, "ROLE_USER", "This is User"));
            userService.saveRole(new Role(null, "ROLE_ADMIN", "This is Admin"));
            userService.saveRole(new Role(null, "ROLE_MANAGER", "This is Manager"));

            userService.saveUser(new User("demo-user", "demouser@gmail.com", "demo123", "(99)999999999", new HashSet<>()));
            userService.saveUser(new User("demo-admin", "demoadmin@gmail.com", "demo123", "(99)999999999", new HashSet<>()));
            userService.saveUser(new User("demo-manager", "demomanager@gmail.com", "demo123", "(99)999999999", new HashSet<>()));

            userService.addToUser("demouser@gmail.com", "ROLE_USER");
            userService.addToUser("demoadmin@gmail.com", "ROLE_ADMIN");
            userService.addToUser("demomanager@gmail.com", "ROLE_MANAGER");
        };
    }
}//class
