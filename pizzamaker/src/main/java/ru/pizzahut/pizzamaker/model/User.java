package ru.pizzahut.pizzamaker.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(schema = "user_management", name = "t_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "c_name")
    private String name;

    @Column(name = "c_password")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(schema = "user_management", name = "t_user_role",
            joinColumns = @JoinColumn(name = "c_user_id"),
            inverseJoinColumns = @JoinColumn(name = "c_role_id"))
    private Set<Role> roles = new HashSet<>();
}
