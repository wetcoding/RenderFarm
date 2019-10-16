package com.wetcoding.renderfarm.models;

import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.util.List;

@Entity
@Table (name = "users")
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    private String email;
    private String password;

    @OneToMany (mappedBy = "users", cascade = CascadeType.ALL)
    private List<Task> tasks;

}
