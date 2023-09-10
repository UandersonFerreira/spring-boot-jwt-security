package br.com.uanderson.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @SequenceGenerator(
            name = "role_sequence",
            sequenceName = "role_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "role_sequence"
    )
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
//Esta anotação é usada para instruir o sistema a não serializar o campo user quando o objeto Role é convertido em formato JSON
    private Set<User> user = new HashSet<>();//O HashSet é usado para garantir que os objetos dentro da coleção sejam exclusivos
    /*
    private Set<User> user = new HashSet<>();
    Em resumo, esse trecho de código configura uma relação muitos-para-muitos entre
    a entidade Role e User. Ela é mapeada pelo campo "roles" na entidade User.
    Além disso, controla a forma como as associações são buscadas usando FetchMode.SELECT
    e evita a serialização recursiva de usuários relacionados com @JsonIgnore.
    O campo user é onde as instâncias de User associadas a esta função são armazenadas
     */

    public Role(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Role(String name) {
        this.name = name;
    }

    @PrePersist
//é usada para marcar um método que deve ser executado antes que uma nova entidade seja persistida no banco de dados
    protected void onCreated() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
//é usada para marcar um método que deve ser executado antes que uma entidade existente seja atualizada no banco de dados.
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

/*
    SOMENTE UM EXEMPLO DE COMO SERIA A CONVERSÃO CASO NECESSÁRIO

 public static Date convertToLocalDateTimeToDate(LocalDateTime localDateTime) {
        // Converte LocalDateTime para um objeto Instant
        // e, em seguida, converte o Instant para Date
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

*/

}
/*

@SequenceGenerator: Esta anotação é usada para definir um gerador
de sequência que será usado para gerar valores para a chave primária.
A chave primária será gerada a partir de uma sequência chamada "role_sequence".
A propriedade allocationSize especifica quantos valores da sequência são
alocados de uma vez. Neste caso, está configurada para alocar um valor por vez.

@GeneratedValue: Esta anotação especifica como a chave primária será gerada.
Neste caso, a estratégia é definida como GenerationType.AUTO, o que significa
que o mecanismo de persistência (como o Hibernate) escolherá a estratégia
apropriada com base no banco de dados subjacente. O atributo generator
especifica o nome do gerador de sequência definido anteriormente (role_sequence)

- Quando você insere um novo registro na tabela users usando JPA, o mecanismo de
 persistência (como o Hibernate) realizará o seguinte:

    - Ele consultará a sequência role_sequence para obter o próximo valor disponível.
    - Ele usará esse valor como a chave primária para o novo registro na tabela users.
    - Por exemplo, se a sequência role_sequence atualmente estiver em 1, o primeiro
        registro inserido na tabela users terá um id de 1.
    - Se você inserir outro registro, a sequência será consultada novamente para obter
        o próximo valor, que será 2, e assim por diante.

Portanto, a sequência role_sequence garante que cada registro na tabela users tenha
um id exclusivo e incremental.

 */