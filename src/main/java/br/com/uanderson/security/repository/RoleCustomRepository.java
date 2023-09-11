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

import javax.xml.crypto.dsig.Transform;
import javax.xml.transform.Transformer;
import java.util.List;

@Repository
public class RoleCustomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Role> getRole(User user){
        StringBuilder stringBuilder = new StringBuilder();
        String sql = """
                   SELECT  r.name as name
                   FROM users u
                   JOIN user_role ur ON u.id = ur.user_id
                   JOIN roles r ON r.id = ur.role_id
                """;
        stringBuilder.append(sql);
        stringBuilder.append("WHERE ur.user_id = ur.role_id");
        if (user.getEmail() != null) stringBuilder.append("AND email =: email");
        NativeQuery nativeQuery = ((Session) entityManager.getDelegate())
                .createNativeQuery(stringBuilder.toString());
        if (user.getEmail() != null) nativeQuery.setParameter("email", user.getEmail());

        nativeQuery.addScalar("name", StandardBasicTypes.STRING);
        nativeQuery.setResultListTransformer(Transformers.aliasToBean(Role.class));

        return nativeQuery.list();
    }

}//class
