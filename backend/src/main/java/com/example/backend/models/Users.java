package com.example.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Access(AccessType.FIELD)
public class Users {
    // ID - первинчый ключ
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    // Поле логина. Имеет тип VARCHAR. Для нас это String
    @Column(name = "login", unique = true, nullable = false)
    public String login;

    // Ну всё-таки скрыть пароль надо бы)
    @JsonIgnore
    @Column(name = "password")
    public String password;

    // Поле - электронная почта
    @Column(name = "email")
    public String email;

    // Поле - соль
    @JsonIgnore
    @Column(name = "salt")
    public String salt;

    // Поле - токен
    @JsonIgnore
    @Column(name = "token")
    public String token;

    // Поле активности
    @Column(name = "activity")
    public LocalDateTime activity;

    // Устанавливаем отношение многим-ко-многим
    // Важно: для отношений многие-ко-многим нужно использовать именно множество, потому что
    // JPA генерирует очень неэффективный код
    @ManyToMany(mappedBy = "users")
    public Set<Museum> museums = new HashSet<>();

    public Users() {}

    public Users(Long id) {
        this.id = id;
    }

    public void addMuseum(Museum m) {
        this.museums.add(m);
        m.users.add(this);
    }

    public void removeMuseum(Museum m) {
        this.museums.remove(m);
        m.users.remove(this);
    }
}
