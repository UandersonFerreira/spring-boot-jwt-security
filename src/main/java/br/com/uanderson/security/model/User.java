package br.com.uanderson.security.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;
    private String username;
    @NaturalId(mutable = true)
    private String email;
    private String password;
    private String mobileNumber;
    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
    private LocalDateTime createdAt;//criando em
    private LocalDateTime updatedAt;//atualizado em

    public User(String username, String email, String password, String mobileNumber, Set<Role> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.mobileNumber = mobileNumber;
        this.roles = roles;
    }

    @PrePersist//é usada para marcar um método que deve ser executado antes que uma nova entidade seja persistida no banco de dados
    protected void onCreated(){
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate//é usada para marcar um método que deve ser executado antes que uma entidade existente seja atualizada no banco de dados.
    protected void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       Collection<SimpleGrantedAuthority> authoritiesList = new ArrayList<>();
       this.roles.stream().forEach(auth -> authoritiesList.add(new SimpleGrantedAuthority(auth.getName())));
       return List.of(new SimpleGrantedAuthority(authoritiesList.toString()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}//class

/*
A anotação @NaturalId é usada para marcar propriedades de entidades
que devem ser consideradas como "identificadores naturais"
e são usadas como chaves únicas nas tabelas do banco de dados.

Aqui estão os detalhes da anotação @NaturalId no Hibernate:

    @NaturalId é usada para sinalizar que uma determinada propriedade de
    uma entidade representa um identificador natural.

    Um identificador natural é uma chave que tem significado semântico no contexto
    do domínio do aplicativo. Por exemplo, o número de passaporte de um indivíduo
    poderia ser um identificador natural em um aplicativo de gerenciamento de passaportes.

    Quando uma propriedade é marcada com @NaturalId, o Hibernate usa essa propriedade
    como um identificador natural no banco de dados.

    Por padrão, as propriedades marcadas com @NaturalId são consideradas como somente
    leitura, o que significa que o Hibernate assume que elas não devem ser modificadas
    após a criação da entidade no banco de dados. Isso é indicado pelo parâmetro mutable
    padrão, que é false.

    Se você definir mutable como true, o Hibernate permitirá que você atualize a
    propriedade marcada com @NaturalId.

 */