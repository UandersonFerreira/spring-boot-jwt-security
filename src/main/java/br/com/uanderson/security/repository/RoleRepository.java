package br.com.uanderson.security.repository;

import br.com.uanderson.security.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName (String roleName);
}
