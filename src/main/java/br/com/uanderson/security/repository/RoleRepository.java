package br.com.uanderson.security.repository;

import br.com.uanderson.security.model.Role;
import br.com.uanderson.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName (String roleName);
    @Query(value = """
            SELECT  r.name, r.description
            FROM users u
            JOIN user_role ur ON u.id = ur.user_id
            JOIN roles r ON r.id = ur.role_id
            WHERE ur.user_id = ur.role_id
            AND u.email = :email;
            """, nativeQuery = true)
    List<Role> getRole(@Param("email") String email);

}
