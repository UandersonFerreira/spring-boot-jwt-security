package br.com.uanderson.security.repository;

import br.com.uanderson.security.model.Role;
import br.com.uanderson.security.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleCustomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Role> getRole(User user) {
        StringBuilder sql = new StringBuilder()
                .append("SELECT r.name as name ")
                .append("FROM users u ")
                .append("JOIN user_role ur ON u.id = ur.user_id ")
                .append("JOIN roles r ON r.id = ur.role_id ")
                .append("WHERE 1=1 ");

        if (user.getEmail() != null) {
            sql.append("AND u.email = :email ");
        }

        NativeQuery<Role> nativeQuery = ((Session) entityManager.getDelegate())
                .createNativeQuery(sql.toString());

        if (user.getEmail() != null) {
            nativeQuery.setParameter("email", user.getEmail());
        }

        nativeQuery.addScalar("name", StandardBasicTypes.STRING);
        nativeQuery.setResultTransformer(Transformers.aliasToBean(Role.class));

        return nativeQuery.list();
    }

}//class
